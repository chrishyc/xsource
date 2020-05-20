package spring.springboot.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

@Data
public class ValidPojo {
    @Range(min = 10, max = 20)
    private int amount;
    
    @NotBlank
    private String name;
}
