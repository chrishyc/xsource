package prometheus.controller;

import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import prometheus.monitor.PrometheusCustomMonitor;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
public class TestController {



  @Resource
  private PrometheusCustomMonitor monitor;


  @RequestMapping("/order")
  public String order() throws Exception {
    // 统计下单次数
    monitor.getOrderCount().increment();
    monitor.getOrderCount1().increment();
    Random random = new Random();
    int amount = random.nextInt(100);
    // 统计金额
    monitor.getAmountSum().record(amount);
    
    return "下单成功, 金额: " + amount;
  }

  @ResponseBody
  @RequestMapping("/alert")
  public String send(@RequestBody String json) {
    System.out.println(json);
    
    return "";
  }
}
