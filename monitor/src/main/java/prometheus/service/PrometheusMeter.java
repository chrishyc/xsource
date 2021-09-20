package prometheus.service;

import com.google.common.base.Throwables;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class PrometheusMeter {
    public static PrometheusMeterRegistry registry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
    
    static {
        registry.config().commonTags("application", "mifi-api-v2");
    }
    
    public static void count(String name, long count) {
        try {
            registry.counter(name).increment(count);
        } catch (Exception e) {
            log.error("PrometheusMeter error,type:{},name:{},tags:{},error:{}", "count", name, "", Throwables.getStackTraceAsString(e));
        }
    }
    
    public static void count(String name, long count, String... tags) {
        try {
            registry.counter(name, tags).increment(count);
        } catch (Exception e) {
            log.error("PrometheusMeter error,type:{}.name:{},tags:{},error:{}", "count", name, tags, Throwables.getStackTraceAsString(e));
        }
    }
    
    public static void histogram(String name, long value) {
        try {
            Timer.builder(name)
                    .publishPercentileHistogram()
                    .register(registry)
                    .record(value, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("PrometheusMeter error,type:{}.name:{},tags:{},error:{}", "histogram", name, "", Throwables.getStackTraceAsString(e));
        }
    }
    
    public static void histogram(String name, long value, Map<String, String> tags) {
        try {
            List<Tag> tagsList = new ArrayList<>();
            tags.forEach((k, v) -> {
                tagsList.add(Tag.of(k, v));
            });
            Timer.builder(name)
                    .publishPercentileHistogram()
                    .tags(tagsList)
                    .register(registry)
                    .record(value, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("PrometheusMeter error,type:{}.name:{},tags:{},error:{}", "histogram", name, tags, Throwables.getStackTraceAsString(e));
        }
    }
    
    public static void histogram(String name, long value, String... tags) {
        try {
            Timer.builder(name)
                    .publishPercentileHistogram()
                    .tags(tags)
                    .register(registry)
                    .record(value, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("PrometheusMeter error,type:{}.name:{},tags:{},error:{}", "histogram", name, tags, Throwables.getStackTraceAsString(e));
        }
    }
    
}
