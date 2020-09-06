package metrics.counter;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jmx.JmxReporter;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

public class CounterTest {
    public static Queue<String> q = new LinkedBlockingQueue<>();
    
    public static Counter pendingJobs;
    
    public static Random random = new Random();
    
    public static void addJob(String job) {
        pendingJobs.inc();
        q.offer(job);
    }
    
    public static String takeJob() {
        pendingJobs.dec();
        return q.poll();
    }
    
    public static void main(String[] args) throws InterruptedException {
        MetricRegistry registry = new MetricRegistry();
//        ConsoleReporter reporter = ConsoleReporter.forRegistry(registry).build();
//        reporter.start(1, TimeUnit.SECONDS);
        JmxReporter reporter = JmxReporter.forRegistry(registry).build();
        reporter.start();
        
        
        pendingJobs = registry.counter(MetricRegistry.name(Queue.class, "pending-jobs", "size"));
        
        int num = 1;
        for (int i = 0; ; i++) {
            Thread.sleep(2000);
            if (random.nextDouble() > 0.7) {
                String job = takeJob();
                System.out.println("take job : " + job);
            } else {
                String job = "Job-" + num;
                addJob(job);
                System.out.println("add job : " + job);
            }
            num++;
        }
    }
}
