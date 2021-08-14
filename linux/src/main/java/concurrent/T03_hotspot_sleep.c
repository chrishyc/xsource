public static native void sleep(long millis) throws InterruptedException;
//最终使用pthread_cond_timedwait


// 如果参数是0的话，可以转换成yield
JVM_ENTRY(void, JVM_Sleep(JNIEnv* env, jclass threadClass, jlong millis))
  // ...
  if (millis == 0) {
    if (ConvertSleepToYield) { // 默认是false
      os::yield();
    } else {
      ThreadState old_state = thread->osthread()->get_state();
      thread->osthread()->set_state(SLEEPING);
      os::sleep(thread, MinSleepInterval, false); // 小睡一下
      thread->osthread()->set_state(old_state);
    }
  } else {
    ThreadState old_state = thread->osthread()->get_state();
    thread->osthread()->set_state(SLEEPING);
    if (os::sleep(thread, millis, true) == OS_INTRPT) {
      // 处理中断
    }
    thread->osthread()->set_state(old_state);
  }
  // ...
JVM_END




int os::sleep(Thread* thread, jlong millis, bool interruptible) {
  ParkEvent * const slp = thread->_SleepEvent ;
  if (interruptible) {
    jlong prevtime = javaTimeNanos();

    for (;;) {
      if (os::is_interrupted(thread, true)) {
        return OS_INTRPT;
      }

      jlong newtime = javaTimeNanos();

      if (newtime - prevtime < 0) {
        // ...
      } else {
        millis -= (newtime - prevtime) / NANOSECS_PER_MILLISEC;
      }

      if(millis <= 0) {
        return OS_OK;
      }
      // ...
      {
        // ...
        slp->park(millis);  // 调用的是os::PlatformEvent::park
        // ...
      }
    }
  } else {
    // ...
  }
}







// 最终是调用ParkEvent的park函数
int os::PlatformEvent::park(jlong millis) {
  int v ;
  for (;;) {
      v = _Event ;
      if (Atomic::cmpxchg (v-1, &_Event, v) == v) break ; // cas设置_Event
  }
  if (v != 0) return OS_OK ;  // os::PlatformEvent::unpark的时候会设置_Event=1，这里就会提前跳出
  struct timespec abst;
  compute_abstime(&abst, millis);  // 0. 计算绝对时间

  int ret = OS_TIMEOUT;
  int status = pthread_mutex_lock(_mutex);  // 1. 加mutex锁
  // ...
  ++_nParked ;

  while (_Event < 0) {
    status = os::Linux::safe_cond_timedwait(_cond, _mutex, &abst); // 2. 等待
    if (status != 0 && WorkAroundNPTLTimedWaitHang) {
      pthread_cond_destroy (_cond);
      pthread_cond_init (_cond, os::Linux::condAttr()) ;
    }
    if (!FilterSpuriousWakeups) break ;                 // previous semantics
    if (status == ETIME || status == ETIMEDOUT) break ;
  }
  --_nParked ;
  if (_Event >= 0) {
     ret = OS_OK;
  }
  _Event = 0 ;
  status = pthread_mutex_unlock(_mutex); // 3. 释放mutex锁
  // ...
  return ret;
}





int os::Linux::safe_cond_timedwait(pthread_cond_t *_cond, pthread_mutex_t *_mutex, const struct timespec *_abstime)
{
   if (is_NPTL()) {
      return pthread_cond_timedwait(_cond, _mutex, _abstime);
   } else {
      int fpu = get_fpu_control_word();
      int status = pthread_cond_timedwait(_cond, _mutex, _abstime);
      set_fpu_control_word(fpu);
      return status;
   }
}

