package prometheus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.util.IOUtils;
import io.prometheus.client.exporter.common.TextFormat;
import org.springframework.web.bind.annotation.*;
import prometheus.model.LattencySample;
import prometheus.monitor.PrometheusCustomMonitor;
import prometheus.service.Cleanable;
import prometheus.service.PrometheusMeter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;

@RestController
public class TestController {
    
    public static ObjectMapper objectMapper = new ObjectMapper();
    @Resource
    private PrometheusCustomMonitor monitor;
    
    
    @RequestMapping("/order")
    public String order() throws Exception {
        // 统计下单次数
//    monitor.getOrderCount().increment();
//    monitor.getOrderCount1().increment();
        Random random = new Random();
        int amount = random.nextInt(100);
        Metrics.counter("dubbo.invoke", "method", "dubbo.hello.fail").increment(1);
        // 统计金额
//    monitor.getAmountSum().record(amount);
        PrometheusCustomMonitor.myRegistry.counter("test.write", "hello", "chris");
        return "下单成功, 金额: " + amount;
    }
    
    @ResponseBody
    @RequestMapping("/alert")
    public String send(@RequestBody String json) {
        System.out.println(json);
        Metrics.counter("dubbo.hello.fail", "2", "1", "hello", "4").increment(1);
        return "";
    }
    
    @GetMapping("/scrape")
    public void scrape(HttpServletResponse response) throws IOException {
        Writer writer = new StringWriter();
        TextFormat.write004(writer, PrometheusMeter.registry.getPrometheusRegistry().metricFamilySamples());
        response.setContentType(TextFormat.CONTENT_TYPE_004);
        response.getWriter().write(writer.toString());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().flush();
    }
    
    @PostMapping("/sample")
    public String sample(HttpServletRequest request) {
        try {
            String req = IOUtils.toString(request.getInputStream());
            LattencySample sample = objectMapper.readValue(req, LattencySample.class);
            sample.getData().stream()
                    .forEach(item -> {
                        PrometheusMeter.histogram(item.getMetricName(), item.getValue(), item.getLabels());
                    });
            System.gc();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }
}
