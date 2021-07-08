package concurrent.cacheline;

public class MultiCpuCache {
    
    private Long total = 0L;
    
    //循环一万次相加
    private void add() {
        for (int i = 0; i < 10000; i++) {
            total += 1;
        }
    }
    
    //开启两个线程相加
    public static void main(String[] args) throws InterruptedException {
        MultiCpuCache thread = new MultiCpuCache();
        //创建两个线程
        Thread thread1 = new Thread(thread::add);
        Thread thread2 = new Thread(thread::add);
        
        //启动线程
        thread1.start();
        thread2.start();
        
        //阻塞主线程
        thread1.join();
        thread2.join();
        System.out.println(thread.total);
    }
}
