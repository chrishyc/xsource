package prometheus;

import io.micrometer.core.instrument.MeterRegistry;
import com.xiaomi.mifi.common.utils.http.MifiHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class PrometheusApplication {

  public static void main(String[] args) {
    SpringApplication.run(PrometheusApplication.class, args);
  }
  @Bean
  MeterRegistryCustomizer<MeterRegistry> configurer(
      @Value("${spring.application.name}") String applicationName) {
    return (registry) -> registry.config().commonTags("application", applicationName);
  }

  @Bean
  public MifiHttpClient mifiHttpClient() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
    return new MifiHttpClient();
  }
}
