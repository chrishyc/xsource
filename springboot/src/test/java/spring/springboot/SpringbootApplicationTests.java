package spring.springboot;

import chris.mystarter.TestBean;
import org.apache.ibatis.annotations.Mapper;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScannerRegistrar;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.*;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.boot.env.RandomValuePropertySource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.*;
import org.springframework.test.context.junit4.SpringRunner;
import spring.springboot.controller.DemoController;
import spring.springboot.mapper.CommentMapper;
import spring.springboot.pojo.*;
import spring.springboot.repository.CommentRepository;
import org.springframework.boot.autoconfigure.data.jpa.*;
import java.util.Optional;
import java.util.Set;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.condition.*;
import spring.springboot.repository.RedisRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
class SpringbootApplicationTests {
    
    @Autowired
    private DemoController demoController;
    
    @Autowired
    private Person person;
    
    @Autowired
    private Dog dog;
    
    @Autowired
    private CustomConfig customConfig;
    
    /**
     * {@link RandomValuePropertySource}
     */
    @Value("${custom.number.inrange}")
    private int range;
    
    @Autowired
    private TestBean testBean;
    
    @Autowired
    private CommentMapper commentMapper;
    
    @Autowired
    private CommentRepository repository;
    
    @Autowired
    private RedisRepository redisRepository;
    
    @Test
    void contextLoads() {
        demoController.sayHello();
    }
    
    
    /**
     * spring.factories加载{@link ConfigFileApplicationListener}
     * 而{@link ConfigFileApplicationListener}会加载application.properties
     * 以及application.yml
     *
     * application.properties中申明的属性都有默认值，在META-INF/spring-configuration-metadata.json中
     *
     */
    @Test
    public void testPropertiesAndYML() {
    
    }
    
    /**
     * {@link EnableConfigurationProperties}
     * {@link ConfigurationProperties}
     * <p>
     * 会import{@link EnableConfigurationPropertiesRegistrar#registerInfrastructureBeans}
     * 会注入{@link ConfigurationPropertiesBindingPostProcessor#postProcessBeforeInitialization},在此方法中进行
     * 查询{@link ConfigurationProperties}注解bean和并对此bean绑定参数.对于没有注入到bean工厂中的bean，不会进行此过程
     * <p>
     * {@link PropertiesPropertySourceLoader#loadProperties}加载配置文件
     */
    @Test
    public void testConfigurationProperties() {
        System.out.println(person);
        System.out.println(person);
    }
    
    /**
     * {@link Value}
     * <p>
     * {@link AutowiredAnnotationBeanPostProcessor#postProcessProperties}完成{@link Value}的注入
     * {@link SpringApplication#load}实例化bean加载器{@link BeanDefinitionLoader},
     * 他的构造函数中会实例化{@link AnnotatedBeanDefinitionReader}{@link ClassPathBeanDefinitionScanner}
     * <p>
     * {@link AnnotatedBeanDefinitionReader}实例化时会注入一些配置处理器{@link AnnotationConfigUtils#registerAnnotationConfigProcessors}
     * 包括{@link AutowiredAnnotationBeanPostProcessor#postProcessProperties}
     */
    @Test
    public void testValue() {
        System.out.println(dog);
        System.out.println(dog);
    }
    
    /**
     * {@link PropertySource}
     * <p>
     * {@link ConfigurationClassParser#doProcessConfigurationClass}注入指定的配置文件
     * <p>
     * {@link ConfigurationClassPostProcessor#processConfigBeanDefinitions}处理 配置bean
     * {@link ConfigurationClassParser#doProcessConfigurationClass}
     * <p>
     * {@link AnnotatedBeanDefinitionReader}实例化时会注入一些配置处理器{@link AnnotationConfigUtils#registerAnnotationConfigProcessors}
     * 包括{@link ConfigurationClassPostProcessor}
     */
    @Test
    public void testCustom() {
        System.out.println(customConfig);
        System.out.println(customConfig);
    }
    
    @Test
    public void testRef() {
        System.out.println(range);
        System.out.println(customConfig);
    }
    
    @Test
    public void testMyStarter() {
        System.out.println(testBean);
        System.out.println(testBean);
    }
    
    /**
     * springboot通过SPI完成对各jar包的自动注入
     * 具体是通过扫描每个jar包中META-INF/spring.factories申明的类
     * <p>
     * mybatis-spring-boot-starter包含mybatis-spring-boot-autoconfigure
     * mybatis-spring-boot-autoconfigure中定义了自定义注解类{@link MybatisAutoConfiguration}
     * <p>
     * 1.{@link MybatisAutoConfiguration.MapperScannerRegistrarNotFoundConfiguration},如果没有
     *
     * @ConditionalOnMissingBean({ MapperFactoryBean.class, MapperScannerConfigurer.class })
     * 则会以springboot的相同包名作为扫描包，
     * @Import({@link MybatisAutoConfiguration.AutoConfiguredMapperScannerRegistrar#registerBeanDefinitions}),其中会注入bean{@link MapperScannerConfigurer}
     * 而{@link MapperScannerConfigurer#postProcessBeanDefinitionRegistry}会扫描所有{@link Mapper}
     * <p>
     * 2.{@link MapperScan}会Import{@link MapperScannerRegistrar},{@link MapperScannerRegistrar#registerBeanDefinitions}剩余操作同流程1
     * <p>
     * 3.{@link ClassPathMapperScanner#registerFilters}确定候补candidate bean
     * {@link ClassPathScanningCandidateComponentProvider#scanCandidateComponents}
     * <p>
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
    
    /**
     * 代码解析流程
     * 1.{@link ConfigurationClassPostProcessor#processConfigBeanDefinitions}处理所有{@link Configuration}配置类
     * 2.{@link ConfigurationClassParser#parse(Set)}解析配置类,这里会处理{@link Import}类，
     * {@link ConfigurationClassParser#processImports},这里处理springboot的import类{@link ConfigurationClassParser.DeferredImportSelectorHandler#handle}
     * {@link ConfigurationClassParser.DeferredImportSelectorHandler#process()}
     * 3.{@link AutoConfigurationImportSelector#filter}过滤，其中{@link OnBeanCondition#getOutcome(Set, Class)}过滤{@link ConditionalOnBean}
     * {@link OnClassCondition}过滤{@link ConditionalOnClass}
     *
     * 配置属性加载流程
     * 1.将spring-autoconfigure-metadata.properties配置加载到properties中
     * 2.当spring.factories加载到JpaRepositoriesAutoConfiguration时，去properties查看他是否有{@link ConditionalOnBean}且容器中是否有此bean
     * {@link ConditionalOnClass}以及classpath是否有此class文件.
     * 3.处理完成后在{@link AutoConfigurationImportSelector#filter}中过滤候选bean
     */
    @Test
    public void selectComment() {
        Optional<Comment> optional = repository.findById(1);
        optional.ifPresent(System.out::println);
    }
    
    /**
     * redis autoConfiguration
     *
     * 1.查看spring-autoconfigure-metadata.properties中有关redis的自动配置
     * {@link org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration}
     * 以及对应的激活条件{@link org.springframework.data.redis.core.RedisOperations}
     *
     * 2.RedisOperations属于spring-data-redis,所有只要导入spring-data-redis就会激活RedisAutoConfiguration
     *   而RedisAutoConfiguration会注入bean{@link org.springframework.data.redis.core.RedisTemplate}
     *   {@link org.springframework.boot.autoconfigure.data.redis.LettuceConnectionConfiguration}
     * 3.此时，{@link org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration}
     * 也会被激活，其中spring.data.redis.repositories默认配置为true,在META-INF/spring-configuration-metadata.json中
     *
     * 激活会导入{@link org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesRegistrar}配置，此时redis就可以使用
     * repository api了
     *
     */
    @Test
    public void redisTest(){
        RedisPojo pojo = new RedisPojo();
        pojo.setFirstName("张");
        pojo.setLastName("三");
    
        RedisAddress address = new RedisAddress();
        address.setCity("北京");
        address.setCountry("中国");
        pojo.setAddress(address);
    
        // 向redis数据库中添加了数据
        redisRepository.save(pojo);
    }

}
