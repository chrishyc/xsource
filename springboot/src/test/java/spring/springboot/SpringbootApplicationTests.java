package spring.springboot;

import chris.mystarter.TestBean;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import spring.springboot.controller.DemoController;
import spring.springboot.mapper.CommentMapper;
import spring.springboot.pojo.Custom;
import spring.springboot.pojo.Dog;
import spring.springboot.pojo.MybatisComment;
import spring.springboot.pojo.Person;
import org.mybatis.spring.boot.autoconfigure.*;
import org.mybatis.spring.mapper.*;
import org.apache.ibatis.annotations.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringbootApplicationTests {
    
    @Autowired
    private DemoController demoController;
    
    @Autowired
    private Person person;
    
    @Autowired
    private Dog dog;
    
    @Autowired
    private Custom custom;
    
    @Value("${custom.number.inrange}")
    private int range;
    
    @Autowired
    private TestBean testBean;
    
    @Autowired
    private CommentMapper commentMapper;
    
    @Test
    void contextLoads() {
        demoController.sayHello();
    }
    
    @Test
    public void testConfigurationProperties() {
        System.out.println(person);
        System.out.println(person);
    }
    
    @Test
    public void testValue() {
        System.out.println(dog);
        System.out.println(dog);
    }
    
    @Test
    public void testCustom() {
        System.out.println(custom);
        System.out.println(custom);
    }
    
    @Test
    public void testRef() {
        System.out.println(range);
        System.out.println(custom);
    }
    
    @Test
    public void testMyStarter() {
        System.out.println(testBean);
        System.out.println(testBean);
    }
    
    /**
     * springboot通过SPI完成对各jar包的自动注入
     * 具体是通过扫描每个jar包中META-INF/spring.factories申明的类
     *
     * mybatis-spring-boot-starter包含mybatis-spring-boot-autoconfigure
     * mybatis-spring-boot-autoconfigure中定义了自定义注解类{@link MybatisAutoConfiguration}
     *
     * 1.@Import(AutoConfiguredMapperScannerRegistrar.class),其中会注入bean{@link MapperScannerConfigurer}
     * 而{@link MapperScannerConfigurer#postProcessBeanDefinitionRegistry}会扫描所有{@link Mapper}
     *
     */
    @Test
    public void testMybatis() {
        MybatisComment comment = commentMapper.findById(1);
        System.out.println(comment);
    }
}
