package prometheus.monitor;

import io.micrometer.core.instrument.*;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * PrometheusScrapeEndpoint#scrape
 */
@Component
public class PrometheusCustomMonitor {
    /**
     * 订单发起次数
     */
    private Counter orderCount;
    
    private Counter orderCount1;
    
    /**
     * 金额统计
     */
    private DistributionSummary amountSum;
    
    private final MeterRegistry registry;
    
    @Autowired
    public PrometheusCustomMonitor(MeterRegistry registry) {
        this.registry = registry;
    }
    
    /**
     * id相同但打点type不同异常
     *
     * @Override public String toString() {
     * return "MeterId{" +
     * "name='" + name + '\'' +
     * ", tags=" + tags +
     * '}';
     * }
     * @Override public boolean equals(@Nullable Object o) {
     * if (this == o) return true;
     * if (o == null || getClass() != o.getClass()) return false;
     * Meter.Id meterId = (Meter.Id) o;
     * return Objects.equals(name, meterId.name) && Objects.equals(tags, meterId.tags);
     * }
     * @Override public int hashCode() {
     * int result = name.hashCode();
     * result = 31 * result + tags.hashCode();
     * return result;
     * }
     */
    @PostConstruct
    private void init() {
//        orderCount = registry.counter("order_request_count", "order", "test-svc");
//        orderCount1 = registry.counter("order_request_count", "order", "test-svc", "name", "1111");
//        amountSum = registry.summary("order_request_count", "orderAmount", "test-svc");
        Metrics.counter("order_request_count", "2", "1", "hello", "4").increment(1);
        Metrics.counter("order_request_count", "2", "1","a","b").increment(1);
//        Timer.builder("order_request_count")
//                .publishPercentileHistogram()
//                .register(Metrics.globalRegistry)
//                .record(1, TimeUnit.MILLISECONDS);
        Timer timer = Timer.builder("order_request_count")
                .publishPercentileHistogram()
                .minimumExpectedValue(Duration.ofNanos(1000))
                .maximumExpectedValue(Duration.ofSeconds(5))
                .tags("2", "1")
                .register(Metrics.globalRegistry);
        timer.record(1, TimeUnit.MILLISECONDS);
    }
    
    public Counter getOrderCount() {
        return orderCount;
    }
    
    public Counter getOrderCount1() {
        return orderCount1;
    }
    
    public DistributionSummary getAmountSum() {
        return amountSum;
    }
    
    public static PrometheusMeterRegistry myRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT) {
        @Override
        public MeterRegistry.Config config() {
            return super.config().commonTags("application", "mifi-api-v2");
        }
    };
}
