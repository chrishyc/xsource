package prometheus.monitor;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PrometheusCustomMonitor {
  /**
   * 订单发起次数
   */
  private Counter orderCount;

  /**
   * 金额统计
   */
  private DistributionSummary amountSum;

  private final MeterRegistry registry;

  @Autowired
  public PrometheusCustomMonitor(MeterRegistry registry) {
    this.registry = registry;
  }

  @PostConstruct
  private void init() {
    orderCount = registry.counter("order_request_count", "order", "test-svc");
    amountSum = registry.summary("order_amount_sum", "orderAmount", "test-svc");
  }

  public Counter getOrderCount() {
    return orderCount;
  }

  public DistributionSummary getAmountSum() {
    return amountSum;
  }
}
