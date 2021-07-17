package concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class T25_Collection_queue_SynchronusQueue { //
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> strs = new SynchronousQueue<>();
        
        new Thread(() -> {
            try {
                System.out.println(strs.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        
        strs.put("aaa"); //
        //strs.put("bbb");
        //strs.add("aaa");
        System.out.println(strs.size());
    }
}
