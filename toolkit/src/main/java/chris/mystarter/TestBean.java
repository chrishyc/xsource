package chris.mystarter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author chris
 */
@EnableConfigurationProperties(value = TestBean.class)
@ConfigurationProperties(prefix = "chris")
public class TestBean {
    private String name;
    private int age;
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
}
