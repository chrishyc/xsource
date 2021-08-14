public final native void wait(long timeout) throws InterruptedException;
//  最终也是调用了os::PlatformEvent::park函数，与sleep的实现方式一致 ,最终使用pthread_cond_timedwait


// 对应的入口是JVM_MonitorWait

JVM_ENTRY(void, JVM_MonitorWait(JNIEnv* env, jobject handle, jlong ms))
  JVMWrapper("JVM_MonitorWait");
  Handle obj(THREAD, JNIHandles::resolve_non_null(handle));
  JavaThreadInObjectWaitState jtiows(thread, ms != 0);
  if (JvmtiExport::should_post_monitor_wait()) {
    JvmtiExport::post_monitor_wait((JavaThread *)THREAD, (oop)obj(), ms);
  }
  ObjectSynchronizer::wait(obj, ms, CHECK);
JVM_END



// Object::wait是配合synchronized使用的，对应的代码是在synchronizer.cpp中
void ObjectSynchronizer::wait(Handle obj, jlong millis, TRAPS) {
  if (UseBiasedLocking) {
    BiasedLocking::revoke_and_rebias(obj, false, THREAD);
    assert(!obj->mark()->has_bias_pattern(), "biases should be revoked by now");
  }
  if (millis < 0) {
    TEVENT (wait - throw IAX) ;
    THROW_MSG(vmSymbols::java_lang_IllegalArgumentException(), "timeout value is negative");
  }
  ObjectMonitor* monitor = ObjectSynchronizer::inflate(THREAD, obj()); // 膨胀为重量级锁
  DTRACE_MONITOR_WAIT_PROBE(monitor, obj(), THREAD, millis);
  monitor->wait(millis, true, THREAD); // 调用wait

  dtrace_waited_probe(monitor, obj, THREAD);
}




void ObjectMonitor::wait(jlong millis, bool interruptible, TRAPS) {
  Thread * const Self = THREAD ;
  // ...
  if (interruptible && Thread::is_interrupted(Self, true) && !HAS_PENDING_EXCEPTION) {
     // ...
     THROW(vmSymbols::java_lang_InterruptedException()); // 处理中断
     return ;
   }
  // ...
  AddWaiter (&node) ; // 1. 添加到ObjectMonitor的等待队列_WaitSet中
  // ...
  exit (true, Self) ; // 2. 释放java的monitor锁（也就是monitorexit）
  // ...
       if (interruptible &&
           (Thread::is_interrupted(THREAD, false) ||
            HAS_PENDING_EXCEPTION)) {
           // Intentionally empty
       } else if (node._notified == 0) {
         if (millis <= 0) {
            Self->_ParkEvent->park () ;
         } else {
            ret = Self->_ParkEvent->park (millis) ; // 3. 等待，和Thread::sleep一样的
         }
       }
  //...
}
