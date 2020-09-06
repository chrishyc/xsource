package metrics.healthcheck;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.jmx.JmxReporter;

import java.util.Map;

public class HealthChecksTest {
    static class DatabaseHealthCheck extends HealthCheck {
        private final boolean database;
        
        public DatabaseHealthCheck(boolean database) {
            this.database = database;
        }
        
        @Override
        public HealthCheck.Result check() {
            if (database) {
                return HealthCheck.Result.healthy();
            } else {
                return HealthCheck.Result.unhealthy("Cannot connect to database");
            }
        }
    }
    
    
    public static void main(String[] args) throws InterruptedException {
        HealthCheckRegistry healthChecks = new HealthCheckRegistry();
        healthChecks.register("postgres1", new DatabaseHealthCheck(true));
        healthChecks.register("postgres2", new DatabaseHealthCheck(false));
        healthChecks.register("postgres3", new DatabaseHealthCheck(true));
        Map<String, HealthCheck.Result> results = healthChecks.runHealthChecks();
        for (int i = 0; i < 20; i++) {
            Thread.sleep(1000);
            for (Map.Entry<String, HealthCheck.Result> entry : results.entrySet()) {
                if (entry.getValue().isHealthy()) {
                    System.out.println(entry.getKey() + " is healthy");
                } else {
                    System.err.println(entry.getKey() + " is UNHEALTHY: " + entry.getValue().getMessage());
                    final Throwable e = entry.getValue().getError();
                    if (e != null) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    
}
