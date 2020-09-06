package metrics.histogram;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.ExponentiallyDecayingReservoir;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jmx.JmxReporter;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class HistogramTest {
    public static Random random = new Random();
    
    public static void main(String[] args) throws InterruptedException {
        MetricRegistry registry = new MetricRegistry();
//        ConsoleReporter reporter = ConsoleReporter.forRegistry(registry).build();
//        reporter.start(1, TimeUnit.SECONDS);
        JmxReporter reporter = JmxReporter.forRegistry(registry).build();
        reporter.start();
        Histogram histogram = new Histogram(new ExponentiallyDecayingReservoir());
        registry.register(MetricRegistry.name(HistogramTest.class, "request", "histogram"), histogram);
        
        for (int i = 0; ; i++) {
            Thread.sleep(1000);
            histogram.update(random.nextInt(100000));
        }
        
    }
}
