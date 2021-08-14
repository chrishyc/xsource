private native void interrupt0();
// 最终调用pthread_cond_signal

//本质是通过unpark唤醒阻塞线程并设置interrupt标志位，不会中断一个正在运行的线程
void os::interrupt(Thread* thread) {
  assert(Thread::current() == thread || Threads_lock->owned_by_self(),
    "possibility of dangling Thread pointer");

  //获取系统native线程对象
  OSThread* osthread = thread->osthread();

  if (!osthread->interrupted()) {
    osthread->set_interrupted(true); //设置中断状态为true
   //内存屏障，使osthread的interrupted状态对其它线程立即可见
    OrderAccess::fence();
    //前文说过，_SleepEvent用于Thread.sleep,线程调用了sleep方法，则通过unpark唤醒
    ParkEvent * const slp = thread->_SleepEvent ;
    if (slp != NULL) slp->unpark() ;
  }

  //_parker用于concurrent相关的锁，此处同样通过unpark唤醒
  if (thread->is_Java_thread())
    ((JavaThread*)thread)->parker()->unpark();
  //Object.wait()唤醒
  ParkEvent * ev = thread->_ParkEvent ;
  if (ev != NULL) ev->unpark() ;

}





void Parker::unpark() {
  int s, status ;
  //加锁
  status = pthread_mutex_lock(_mutex);
  assert (status == 0, "invariant") ;
  s = _counter;
  //正常unpark完成counter等于1，park完成counter等于0
  _counter = 1;
  if (s < 1) {
    // thread might be parked
    if (_cur_index != -1) {
      //WorkAroundNPTLTimedWaitHang默认值为1
      if (WorkAroundNPTLTimedWaitHang) {
        //发送信号，唤醒目标线程
        status = pthread_cond_signal (&_cond[_cur_index]);
        assert (status == 0, "invariant");
        //解锁
        status = pthread_mutex_unlock(_mutex);
        assert (status == 0, "invariant");
      } else {
        int index = _cur_index;
        //先解锁
        status = pthread_mutex_unlock(_mutex);
        assert (status == 0, "invariant");
        //再唤醒
        status = pthread_cond_signal (&_cond[index]);
        assert (status == 0, "invariant");
      }
    } else {
      //_cur_index等于-1，线程从休眠状态被唤醒后就是-1了
      pthread_mutex_unlock(_mutex);
      assert (status == 0, "invariant") ;
    }
  } else {
    //_counter大于或者等于1，说明其已经执行过unpark了，不需要再次唤醒了
    pthread_mutex_unlock(_mutex);
    assert (status == 0, "invariant") ;
  }
}

