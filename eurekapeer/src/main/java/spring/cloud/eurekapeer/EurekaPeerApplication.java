package spring.cloud.eurekapeer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaPeerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaPeerApplication.class, args);
    }

}
