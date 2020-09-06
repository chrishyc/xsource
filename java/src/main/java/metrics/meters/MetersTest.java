package metrics.meters;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.TimeUnit;

public class MetersTest {
    static final MetricRegistry metrics = new MetricRegistry();
    
    public static void main(String[] args) {
        startReport();
        Meter requests = metrics.meter("requests");
        for (int i = 0; i < 5; i++) {
            requests.mark();
        }
        wait5Seconds();
    }
    
    static void startReport() {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);
    }
    
    static void wait5Seconds() {
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
        }
    }
}
