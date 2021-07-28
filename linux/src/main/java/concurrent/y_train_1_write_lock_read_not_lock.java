package concurrent;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 脏读，存在中间态，未保证数据的一致性
 */
public class y_train_1_write_lock_read_not_lock {
    String name;
    double balance;
    
    public synchronized void set(String name, double balance) {
        this.name = name;
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("sleep 2000 end");
        
        this.balance = balance;
    }
    
    public /*synchronized*/ double getBalance(String name) {
        return this.balance;
    }
    
    
    public static void main(String[] args) {
        y_train_1_write_lock_read_not_lock a = new y_train_1_write_lock_read_not_lock();
        new Thread(() -> a.set("zhangsan", 100.0)).start();
        
        //保证上面线程能够初始化完成并执行
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("name:zhangsan,balance:" + a.getBalance("zhangsan"));
        
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("name:zhangsan,balance:" + a.getBalance("zhangsan"));
    }
    
    @Test
    public void test() {
        ExecutorService service = new ThreadPoolExecutor(10, 12, 10, TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(), (r, executor) -> {
            System.out.println("fuck,why so many task?");
        });
        DirtyRead dirtyRead = new DirtyRead();
        while (true) {
            service.execute(() -> {
                Data data = new Data();
                data.money = 100;
                data.name = "device";
                dirtyRead.getFakeCOWList().set(0, data);
            });
            service.execute(() -> {
                if (dirtyRead.getFakeCOWList().get(0).name.equals("device") && dirtyRead.getFakeCOWList().get(0).money == 123) {
                    System.out.println("dirty read occur!");
                }
            });
        }
    }
    
    class Data {
        int money;
        String name;
    }
    
    class DirtyRead {
        public List<Data> fakeCOWList = new ArrayList<>();
        
        public List<Data> getFakeCOWList() {
            return fakeCOWList;
        }
        
        {
            Data data = new Data();
            data.money = 123;
            data.name = "chris";
            fakeCOWList.add(data);
        }
        
        public synchronized void set(int i, Data data) {
            fakeCOWList.set(i, data);
        }
        
        public Data get(int i) {
            return fakeCOWList.get(i);
        }
        
        
    }
}
