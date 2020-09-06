package spring.cloud.eureka.servo;

import com.netflix.servo.annotations.DataSourceType;
import com.netflix.servo.annotations.Monitor;
import com.netflix.servo.monitor.Monitors;
import com.netflix.servo.publish.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class Server {
    @Monitor(name = "Status", type = DataSourceType.INFORMATIONAL)
    private static AtomicReference<String> status = new AtomicReference<String>("UP");
    @Monitor(name = "Connections", type = DataSourceType.GAUGE)
    private static AtomicInteger currentConnections = new AtomicInteger(0);
    @Monitor(name = "TotalConnections", type = DataSourceType.COUNTER)
    private static AtomicInteger totalConnections = new AtomicInteger(0);
    @Monitor(name = "BytesIn", type = DataSourceType.COUNTER)
    private static AtomicLong bytesIn = new AtomicLong(0L);
    @Monitor(name = "BytesOut", type = DataSourceType.COUNTER)
    private static AtomicLong bytesOut = new AtomicLong(0L);
    
    private static PollScheduler scheduler = PollScheduler.getInstance();
    
    private Server() {
    }
    
    @Monitor(name = "sayHi", type = DataSourceType.COUNTER)
    private static String hello() {
        return "hello servo";
    }
    
    public static void main(String[] args) {
        Server s1 = new Server();
        Monitors.registerObject("s1", s1);
//        startPoller();
        try {
            TimeUnit.SECONDS.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
    
    public static void startPoller() {
        scheduler.start();
        final int heartbeatInterval = 1200;
        
        final File metricsDir;
        try {
            metricsDir = File.createTempFile("zuul-servo-metrics-", "");
            metricsDir.delete();
            metricsDir.mkdir();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        System.out.println("log dir: " + metricsDir);
        
        MetricObserver transform = new CounterToRateMetricTransform(
                new FileMetricObserver("ZuulMetrics", metricsDir),
                heartbeatInterval, 10, TimeUnit.SECONDS);
        
        PollRunnable task = new PollRunnable(
                new MonitorRegistryMetricPoller(),
                BasicMetricFilter.MATCH_ALL,
                transform);
        
        final int samplingInterval = 1;//采集间隔1s一次采集，所以会输出两个文件
        scheduler.addPoller(task, samplingInterval, TimeUnit.SECONDS);
    }
}
