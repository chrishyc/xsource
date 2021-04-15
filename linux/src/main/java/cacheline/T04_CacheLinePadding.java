package cacheline;

public class T04_CacheLinePadding {
    public static volatile long[] arr = new long[2];
    
    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10_0000_0000; i++) {
                arr[0] = i;
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10_0000_0000; i++) {
                arr[1] = i;
            }
        });
        final long start = System.nanoTime();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println((System.nanoTime() - start) / 100_0000);
    }
}
