public native void park(boolean isAbsolute, long time);
// 最终仍然是依赖于pthread_cond_timedwait来阻塞线程，与sleep不同的是，如果参数是0，park会一直阻塞


// 和sleep、wait不同的是，这里调用的是Parker的park函数，而不是os::PlatformEvent::park
UNSAFE_ENTRY(void, Unsafe_Park(JNIEnv *env, jobject unsafe, jboolean isAbsolute, jlong time))
  // ...
  thread->parker()->park(isAbsolute != 0, time);
  // ...
UNSAFE_END





void Parker::park(bool isAbsolute, jlong time) {
  if (Thread::is_interrupted(thread, false)) {
    return;
  }

  timespec absTime;
  if (time < 0 || (isAbsolute && time == 0) ) { // don't wait at all
    return;
  }
  if (time > 0) {
    unpackTime(&absTime, isAbsolute, time); // 0. 计算绝对时间
  }
  if (Thread::is_interrupted(thread, false) ||
      pthread_mutex_trylock(_mutex) != 0) { // 1. 尝试加mutex锁
    return;
  }
  int status ;
  if (_counter > 0)  { // no wait needed
    _counter = 0;
    status = pthread_mutex_unlock(_mutex); // 2.1 在park之前调用了unpark，就不会wait了
    // ...
    return;
  }
  if (time == 0) {
    _cur_index = REL_INDEX; // arbitrary choice when not timed
    status = pthread_cond_wait (&_cond[_cur_index], _mutex) ;  // 2.2 入参为0，一直等待
  } else {
    _cur_index = isAbsolute ? ABS_INDEX : REL_INDEX;
    // 2.3 带超时的等待
    status = os::Linux::safe_cond_timedwait (&_cond[_cur_index], _mutex, &absTime) ;
    if (status != 0 && WorkAroundNPTLTimedWaitHang) {
      pthread_cond_destroy (&_cond[_cur_index]) ;
      pthread_cond_init    (&_cond[_cur_index], isAbsolute ? NULL : os::Linux::condAttr());
    }
  }
  _counter = 0 ;
  status = pthread_mutex_unlock(_mutex) ; // 3. 释放mutex锁
}

