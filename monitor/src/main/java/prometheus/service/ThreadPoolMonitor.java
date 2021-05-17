package prometheus.service;

import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * https://juejin.cn/post/6844903821387235342
 */
@Service
public class ThreadPoolMonitor implements InitializingBean {
    private static final String EXECUTOR_NAME = "ThreadPoolMonitorSample";
    private static final Iterable<Tag> TAG = Collections.singletonList(Tag.of("thread.pool.name", EXECUTOR_NAME));
    private final ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
    
    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10), new ThreadFactory() {
        
        private final AtomicInteger counter = new AtomicInteger();
        
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            thread.setName("thread-pool-" + counter.getAndIncrement());
            return thread;
        }
    }, new ThreadPoolExecutor.AbortPolicy());
    
    
    private Runnable monitor = () -> {
        //这里需要捕获异常,尽管实际上不会产生异常,但是必须预防异常导致调度线程池线程失效的问题
        try {
            Metrics.gauge("thread.pool.core.size", TAG, executor, ThreadPoolExecutor::getCorePoolSize);
            Metrics.gauge("thread.pool.largest.size", TAG, executor, ThreadPoolExecutor::getLargestPoolSize);
            Metrics.gauge("thread.pool.max.size", TAG, executor, ThreadPoolExecutor::getMaximumPoolSize);
            Metrics.gauge("thread.pool.active.size", TAG, executor, ThreadPoolExecutor::getActiveCount);
            Metrics.gauge("thread.pool.thread.count", TAG, executor, ThreadPoolExecutor::getPoolSize);
            // 注意如果阻塞队列使用无界队列这里不能直接取size
            Metrics.gauge("thread.pool.queue.size", TAG, executor, e -> e.getQueue().size());
        } catch (Exception e) {
            //ignore
        }
    };
    
    @Override
    public void afterPropertiesSet() throws Exception {
        // 每5秒执行一次
        scheduledExecutor.scheduleWithFixedDelay(monitor, 0, 5, TimeUnit.SECONDS);
    }
    
    public void shortTimeWork() {
        executor.execute(() -> {
            try {
                // 5秒
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                //ignore
            }
        });
    }
    
    public void longTimeWork() {
        executor.execute(() -> {
            try {
                // 500秒
                Thread.sleep(5000 * 100);
            } catch (InterruptedException e) {
                //ignore
            }
        });
    }
    
    public void clearTaskQueue() {
        executor.getQueue().clear();
    }
}
