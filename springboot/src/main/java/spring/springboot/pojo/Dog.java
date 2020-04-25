package spring.springboot.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class Dog {
    @Value("${dog.name}")
    private String name;
    @Value("${dog.age}")
    private int age;
}
