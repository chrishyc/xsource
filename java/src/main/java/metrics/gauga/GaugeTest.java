package metrics.gauga;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class GaugeTest {
    public static Queue<String> q = new LinkedList<>();
    
    public static void main(String[] args) throws InterruptedException {
        MetricRegistry registry = new MetricRegistry();
        ConsoleReporter reporter = ConsoleReporter.forRegistry(registry).build();
        reporter.start(1, TimeUnit.SECONDS);
        
        registry.register(MetricRegistry.name(GaugeTest.class, "queue", "size"),
                (Gauge<Integer>) () -> q.size());
        
        for (int i = 0; i < 20; i++) {
            Thread.sleep(1000);
            q.add("Job-xxx");
        }
    }
}
