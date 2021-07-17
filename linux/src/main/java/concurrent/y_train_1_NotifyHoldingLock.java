package concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class y_train_1_NotifyHoldingLock { //wait notify
    
    volatile List lists = new ArrayList();
    
    public void add(Object o) {
        lists.add(o);
    }
    
    public int size() {
        return lists.size();
    }
    
    public static void main(String[] args) {
        y_train_1_NotifyHoldingLock c = new y_train_1_NotifyHoldingLock();
        
        final Object lock = new Object();
        
        new Thread(() -> {
            synchronized (lock) {
                System.out.println("t2 enter");
                if (c.size() != 5) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2 exit");
            }
            
        }, "t2").start();
        
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        
        new Thread(() -> {
            System.out.println("t1 enter");
            synchronized (lock) {
                for (int i = 0; i < 10; i++) {
                    c.add(new Object());
                    System.out.println("add " + i);
                    
                    if (c.size() == 5) {
                        lock.notify();//不会释放锁
                    }
                    
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t1").start();
        
        
    }
}
