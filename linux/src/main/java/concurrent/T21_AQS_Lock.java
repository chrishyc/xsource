package concurrent;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class T21_AQS_Lock implements Lock {
    private Sync sync = new Sync();
    
    @Override
    public void lock() {
        sync.acquire(1);
    }
    
    @Override
    public void unlock() {
        sync.release(1);
    }
    
    @Override
    public void lockInterruptibly() throws InterruptedException {
    
    }
    
    @Override
    public boolean tryLock() {
        return false;
    }
    
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }
    
    @Override
    public Condition newCondition() {
        return null;
    }
    
    class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }
        
        @Override
        protected boolean tryRelease(int arg) {
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }
        
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }
    }
}
