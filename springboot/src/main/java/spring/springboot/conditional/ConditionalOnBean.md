OnBeanCondition原理
##1.需求
存在指定bean实例对象时,才实例化
##2.方案
ConfigurationClassPostProcessor生成beandefinition时判断是否有bean实例

##3.实现
ConfigurationClassBeanDefinitionReader.loadBeanDefinitionsForConfigurationClass时判断是否存在指定
beanDefinition,存在,说明之后会存在该bean实例。

条件注解的解析一定发生在spring ioc的bean definition阶段，因为 spring bean初始化的前提条件就是有对应的bean definition，条件注解正是通过判断bean definition来控制bean能否实例化

在springioc的过程中，优先解析@Component，@Service，@Controller注解的类。其次解析配置类，也就是@Configuration标注的类。最后开始解析配置类中定义的bean。
示例代码中bean1是定义在配置类中的，当执行到配置类解析的时候，@Component，@Service，@Controller ,@Configuration标注的类已经全部被解析，所以这些BeanDifinition已经被同步

如果两个Bean都是配置类中Bean，此时配置类的解析无法保证先后顺序，就会出现不生效的情况

如果依赖的是FeignClient，也有可能会出现不生效的情况。因为FeignClient最终还是由配置类触发，解析的先后顺序也不能保证

##设计模式:
模板设计模式
##语法:
java8 forEach
##命名:
readerInstanceCache
Metadata
Spec
