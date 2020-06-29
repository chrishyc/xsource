package jmx.metrics;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.Notification;
import javax.management.NotificationEmitter;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class GC {
    private static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
    private static CountDownLatch latch = new CountDownLatch(1);
    
    public static void main(String[] args) throws InterruptedException {
        List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcBean : gcBeans) {
            
            NotificationEmitter emitter = (NotificationEmitter) gcBean;
            
            NotificationListener listener = (notification, handback) -> {
                //get gc info
                GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo
                        .from((CompositeData) notification.getUserData());
                //output
                String gcType = info.getGcAction();
                System.out.println(gcType);
                System.out.println(info.getGcInfo());
            };
            //add the listener
            emitter.addNotificationListener(listener, new NotificationFilter() {
                private static final long serialVersionUID = 3763793138186359389L;
                
                @Override
                public boolean isNotificationEnabled(Notification notification) {
                    return notification.getType()
                            .equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION);
                }
            }, null);
        }
        executor.execute(() -> {
            for (int i = 0; i < 1000; i++) {
                int[] allocate = new int[4048];
                allocate[0] = 1;
                int a = allocate[0];
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            latch.countDown();
        });
        latch.await();
        executor.shutdown();
    }
}
