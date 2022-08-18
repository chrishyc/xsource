package activiti;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Ignore
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ActivitiApplication {

    public static void main(String[] args) {
        new ArrayList<>(Collections.singletonList("11"));
        SpringApplication.run(ActivitiApplication.class, args);
    }

}
