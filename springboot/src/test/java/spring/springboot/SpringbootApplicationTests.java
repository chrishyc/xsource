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
import org.mybatis.spring.annotation.*;
import org.springframework.context.annotation.*;
import org.mybatis.spring.boot.autoconfigure.*;

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
     * 1.{@link MybatisAutoConfiguration.MapperScannerRegistrarNotFoundConfiguration},如果没有
     * @ConditionalOnMissingBean({ MapperFactoryBean.class, MapperScannerConfigurer.class })
     * 则会以springboot的相同包名作为扫描包，
     * @Import({@link MybatisAutoConfiguration.AutoConfiguredMapperScannerRegistrar#registerBeanDefinitions}),其中会注入bean{@link MapperScannerConfigurer}
     * 而{@link MapperScannerConfigurer#postProcessBeanDefinitionRegistry}会扫描所有{@link Mapper}
     *
     * 2.{@link MapperScan}会Import{@link MapperScannerRegistrar},{@link MapperScannerRegistrar#registerBeanDefinitions}剩余操作同流程1
     *
     * 3.{@link ClassPathMapperScanner#registerFilters}确定候补candidate bean
     *  {@link ClassPathScanningCandidateComponentProvider#scanCandidateComponents}
     *
     * 总结:两种自动注入mapper接口方式
     * a.{@link Mapper}会过滤所有实现了此注解的接口
     * b.{@link MapperScan}会过滤指定包名下的所有接口
     * c.如果存在{@link MapperScan}则不会再启用{@link Mapper}
     * 判断逻辑在{@link MybatisAutoConfiguration.MapperScannerRegistrarNotFoundConfiguration}
     */
    @Test
    public void testMybatis() {
        MybatisComment comment = commentMapper.findById(1);
        System.out.println(comment);
    }
}
