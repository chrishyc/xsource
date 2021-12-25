# 源码案例

## 1、FactoryBean接口的使用

​		一般情况下，Spring通过反射机制利用bean的class属性指定实现类来实例化bean。在某些情况下，实例化bean过程比较复杂，如果按照传统的方式，则需要在<bean>标签中提供大量的配置信息，配置方式的灵活性是受限的。为此，Spring可以通过实现FactoryBean的接口来定制实例化bean的逻辑。

​		1、创建Car对象

```java
package com.mashibing.test;

public class Car {

    private String name;
    private String brand;
    private Integer speed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", brand=" + brand +
                ", speed=" + speed +
                '}';
    }
}

```

​		2、创建CarFactoryBean

```java
package com.mashibing.test;

import org.springframework.beans.factory.FactoryBean;

public class CarFactoryBean implements FactoryBean<Car> {

    private String carInfo;

    public String getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(String carInfo) {
        this.carInfo = carInfo;
    }

    @Override
    public Car getObject() throws Exception {

        Car car = new Car();
        String[] split = carInfo.split(",");
        car.setName(split[0]);
        car.setBrand(split[1]);
        car.setSpeed(Integer.valueOf(split[2]));
        return  car;
    }

    @Override
    public Class<?> getObjectType() {
        return Car.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
```

​		3、修改配置文件

```xml
    <bean id="car" class="com.mashibing.test.CarFactoryBean" >
        <property name="carInfo" value="大黄蜂,玛莎拉蒂,250"></property>
    </bean>
```

​		4、测试代码

```java
public class MyTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("test.xml");
        Car car=(Car)context.getBean("car");
        System.out.println(car);
    }
}
```

## 2、扩展initPropertySources方法

​		1、继承具体的类并扩展实现

```java
package com.mashibing.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyClassPathXmlApplicationContext extends ClassPathXmlApplicationContext {
    public MyClassPathXmlApplicationContext(String... configLocations){
        super(configLocations);
    }

    @Override
    protected void initPropertySources() {
        getEnvironment().setRequiredProperties("OS");
    }
}
```

​		2、编写测试类

```java
public class MyTest {
    public static void main(String[] args) {
        ApplicationContext context = new MyClassPathXmlApplicationContext("test2.xml");
        User user=(User)context.getBean("testbean");
        System.out.println("username:"+user.getUserName()+"  "+"email:"+user.getEmail());
    }
}
```

## 3、扩展实现customizeBeanFactory方法

​		此方法是用来实现BeanFactory的属性设置，主要是设置两个属性：

​		allowBeanDefinitionOverriding：是否允许覆盖同名称的不同定义的对象

​		allowCircularReferences：是否允许bean之间的循环依赖

```java
public class MyClassPathXmlApplicationContext extends ClassPathXmlApplicationContext {

    MyClassPathXmlApplicationContext(String... locations){
        super(locations);
    }

    @Override
    protected void customizeBeanFactory(DefaultListableBeanFactory beanFactory) {
        super.setAllowBeanDefinitionOverriding(true);
        super.setAllowCircularReferences(true);
        super.customizeBeanFactory(beanFactory);
    }
}

```

## 4、自定义配置文件标签

​		1、User.java

```java
package com.mashibing.selftag;

public class User {
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
```

​		2、UserBeanDefinitionParser.java

```java
package com.mashibing.selftag;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class UserBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @SuppressWarnings("rawtypes")
    protected Class getBeanClass(Element element) {
        return User.class;
    }

    protected void doParse(Element element, BeanDefinitionBuilder bean) {
        String userName = element.getAttribute("userName");
        String email = element.getAttribute("email");
        if (StringUtils.hasText(userName)) {
            bean.addPropertyValue("userName", userName);
        }
        if (StringUtils.hasText(email)){
            bean.addPropertyValue("email", email);
        }

    }
}
```

​		3、MyNamespaceHandler.java

```java
package com.mashibing.selftag;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class MyNamespaceHandler extends NamespaceHandlerSupport {

    public void init() {

        registerBeanDefinitionParser("msb", new UserBeanDefinitionParser());
    }

}
```

​		4、在resource目录下创建META-INF目录下，并创建三个文件

Spring.handlers

```properties
http\://www.mashibing.com/schema/user=com.mashibing.selftag.MyNamespaceHandler
```

Spring.schemas

```properties
http\://www.mashibing.com/schema/user.xsd=META-INF/user.xsd
```

user.xsd

```xml
<?xml version="1.0" encoding="UTF-8"?>
<schema xmlns="http://www.w3.org/2001/XMLSchema"
        targetNamespace="http://www.mashibing.com/schema/user"
        xmlns:tns="http://www.mashibing.com/schema/user"
        elementFormDefault="qualified">
    <element name="msb">
        <complexType>
            <attribute name ="id" type = "string"/>
            <attribute name ="userName" type = "string"/>
            <attribute name ="email" type = "string"/>
        </complexType>
    </element>
</schema>
```

​		5、创建配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aaa="http://www.mashibing.com/schema/user"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
    http://www.mashibing.com/schema/user http://www.mashibing.com/schema/user.xsd">

    <aaa:msb id = "testbean" userName = "lee" email = "bbb"/>
</beans>
```

​		6、编写测试类

```java
public class MyTest {
    public static void main(String[] args) {
        ApplicationContext context = new MyClassPathXmlApplicationContext("test2.xml");
        User user=(User)context.getBean("testbean");
        System.out.println("username:"+user.getUserName()+"  "+"email:"+user.getEmail());
    }
}
```

## 5、ignoreDependencyInterface与ignoreDependencyType

​		在阅读源码的时候，很多同学发现有这样的两个方法：

```java
		/**
	 * 自动装配时忽略的类
	 * 
	 * Ignore the given dependency type for autowiring:
	 * for example, String. Default is none.
	 * @param type the dependency type to ignore
	 */
	void ignoreDependencyType(Class<?> type);

	/**
	 * 自动装配时忽略的接口
	 * 
	 * Ignore the given dependency interface for autowiring.
	 * <p>This will typically be used by application contexts to register
	 * dependencies that are resolved in other ways, like BeanFactory through
	 * BeanFactoryAware or ApplicationContext through ApplicationContextAware.
	 * <p>By default, only the BeanFactoryAware interface is ignored.
	 * For further types to ignore, invoke this method for each type.
	 * @param ifc the dependency interface to ignore
	 * @see org.springframework.beans.factory.BeanFactoryAware
	 * @see org.springframework.context.ApplicationContextAware
	 */
	void ignoreDependencyInterface(Class<?> ifc);
```

​		这两个方法在实际使用的时候，应用的并不是很多或者几乎不用，有兴趣的同学可以去看https://www.jianshu.com/p/3c7e0608ff1f这个帖子，了解他们的详细使用方法和区别。

## 6、自定义属性编辑器

​		在日常的工作中，我们经常遇到一些特殊的案例需要自定义属性的解析器来完成对应的属性解析工作，大家需要理解它的本质来进行随意的扩展工作,但是此处的扩展没有大家想象的那么简单，详细的流程讲课的时候我大概讲一下，但是要复杂很多。主要有两种方式：

第一种方式：

Address.java

```java
package com.mashibing.propertyEditor;

class Address {
    private String district;
    private String city;
    private String province;

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String toString() {
        return this.province + "省" + this.city + "市" + this.district + "区";
    }
}
```

Customer.java

```java
package com.mashibing.propertyEditor;

public class Customer {
    private String name;
    private Address address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
```

AddressPropertyEditor.java

```java
package com.mashibing.propertyEditor;

import java.beans.PropertyEditorSupport;

public class AddressPropertyEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) {
        try {
            String[] adds = text.split("-");
            Address address = new Address();
            address.setProvince(adds[0]);
            address.setCity(adds[1]);
            address.setDistrict(adds[2]);
            this.setValue(address);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

MyPropertyEditorRegistrar.java

```java
package com.mashibing.propertyEditor;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;

public class MyPropertyEditorRegistrar implements PropertyEditorRegistrar {
    @Override
    public void registerCustomEditors(PropertyEditorRegistry registry) {
        registry.registerCustomEditor(Address.class,new AddressPropertyEditor());
    }
}

```

propertyEditor.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="customer" class="com.mashibing.propertyEditor.Customer">
        <property name="name" value="Jack" />
        <property name="address" value="浙江-杭州-西湖" />
    </bean>
    <!--第一种方式-->
    <bean class="org.springframework.beans.factory.config.CustomEditorConfigurer">
        <property name="propertyEditorRegistrars">
            <list>
                <bean class="com.mashibing.propertyEditor.MyPropertyEditorRegistrar"></bean>
            </list>
        </property>
    </bean>
    <!--第二种方式-->
    <bean class="org.springframework.beans.factory.config.CustomEditorConfigurer">
        <property name="customEditors">
            <map>
                <entry key="com.mashibing.propertyEditor.Address">
                    <value>com.mashibing.propertyEditor.AddressPropertyEditor</value>
                </entry>
            </map>
        </property>
    </bean>
</beans>
```

Test.java

```java
import com.mashibing.ignore.ListHolder;
import com.mashibing.propertyEditor.Customer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test3 {

    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("propertyEditor.xml");
        Customer c = ac.getBean("customer", Customer.class);
        //输出
        System.out.println(c.getAddress());
    }
}
```

## 7、如何向beanFactoryPostProcessors中添加自定义的BeanFactoryPostProcessor

​		其实在之前的课程中，我做过演示，如何添加自定义的BeanFactoryPostProcessors，只需要在xml文件中声明成为一个bean即可，但是在此处我们如何进行扩展呢？

```java
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyClassPathXmlApplicationContext extends ClassPathXmlApplicationContext {


    public MyClassPathXmlApplicationContext(String... configLocations){
        super(configLocations);
    }

    @Override
    protected void initPropertySources() {
        System.out.println("扩展initPropertySource");
        getEnvironment().setRequiredProperties("username");
    }

    @Override
    protected void customizeBeanFactory(DefaultListableBeanFactory beanFactory) {
        super.setAllowBeanDefinitionOverriding(false);
        super.setAllowCircularReferences(false);
        super.addBeanFactoryPostProcessor(new MyBeanFactoryPostProcessor());
        super.customizeBeanFactory(beanFactory);
    }

}
```

## 8、如何在BeanDefinitionRegistryPostProcessor执行过程中添加其他的BeanDefinitionRegistryPostProcessor

MyBeanDefinition

```java
package com.mashibing;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.ResolvableType;

public class MyBeanDefinition implements BeanDefinitionRegistryPostProcessor, Ordered {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MyBeanDefinition{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("postProcessBeanDefinitionRegistry----------------------");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("postProcessBeanFactory========================");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}

```

MyBeanDefinitionRegistryPostProcessor

```java
package com.mashibing;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, Ordered {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(MyBeanDefinition.class);
        builder.addPropertyValue("name","zhangsan");
        registry.registerBeanDefinition("aa",builder.getBeanDefinition());
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("MyBeanDefinitionRegistryPostProcessor-------");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}

```

applicationContext.xml

```xml
<bean class="com.mashibing.MyBeanDefinitionRegistryPostProcessor"></bean>
```

## 9、自定义类型转换器

Student.java

```java
package com.mashibing.selfConverter;

public class Student {

    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
```

StudentConverter.java

```java
package com.mashibing.selfConverter;

import org.springframework.core.convert.converter.Converter;

public class StudentConverter implements Converter<String,Student> {
    @Override
    public Student convert(String source) {
        System.out.println("-----");
        Student s  = new Student();
        String[] splits = source.split("_");
        s.setId(Integer.parseInt(splits[0]));
        s.setName(splits[1]);
        return s;
    }
}
```

Test.java

```java
public class Test {

    public static void main(String[] args) {
        MyClassPathXmlApplicationContext ac = new MyClassPathXmlApplicationContext("applicationContext.xml");
        ConversionService bean = ac.getBean(ConversionService.class);
        Student convert = bean.convert("1_zhangsan", Student.class);
        System.out.println(convert);
    }
}
```

## 10、lookup-method标签使用

单例模式的bean只会被创建一次，IoC容器会缓存该bean实例以供下次使用；原型模式的bean每次都会创建一个全新的bean，IoC容器不会缓存该bean的实例。那么如果现在有一个单例模式的bean引用了一个原型模式的bean呢？如果无特殊处理，则被引用的原型模式的bean也会被缓存，这就违背了原型模式的初衷，这时使用lookup-method注入可以解决该问题。

```java
package com.mashibing.methodOverrides.lookup;

public class Fruit {
    public Fruit() {
        System.out.println("I got Fruit");
    }
}
package com.mashibing.methodOverrides.lookup;

public class Apple extends Fruit {
    public Apple() {
        System.out.println("I got a fresh apple");
    }
}
package com.mashibing.methodOverrides.lookup;

public class Banana extends Fruit {
    public Banana() {
        System.out.println("I got a  fresh bananer");
    }
}
package com.mashibing.methodOverrides.lookup;

public abstract class FruitPlate{
    // 抽象方法获取新鲜水果
    public abstract Fruit getFruit();
}
```

配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="apple" class="com.mashibing.methodOverrides.lookup.Apple" scope="prototype"/>
    <bean id="banana" class="com.mashibing.methodOverrides.lookup.Banana" scope="prototype" />

    <bean id="fruitPlate1" class="com.mashibing.methodOverrides.lookup.FruitPlate">
        <lookup-method name="getFruit" bean="apple"/>
    </bean>
    <bean id="fruitPlate2" class="com.mashibing.methodOverrides.lookup.FruitPlate">
        <lookup-method name="getFruit" bean="banana"/>
    </bean>
</beans>
```

Test.java

```java
package com.mashibing;

import com.mashibing.methodOverrides.lookup.FruitPlate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestMethodOverrides {

    public static void main(String[] args) {
        ApplicationContext app = new ClassPathXmlApplicationContext("bean.xml");

        FruitPlate fp1= (FruitPlate)app.getBean("fruitPlate1");
        FruitPlate fp2 = (FruitPlate)app.getBean("fruitPlate2");

        fp1.getFruit();
        fp2.getFruit();
    }
}
```

## 11、replace-method标签使用

替换方法体及其返回值

```java
package com.mashibing.methodOverrides.replace;

public class OriginalDog {
	public void sayHello() {
		System.out.println("Hello,I am a black dog...");
	}

	public void sayHello(String name) {
		System.out.println("Hello,I am a black dog, my name is " + name);
	}
}

package com.mashibing.methodOverrides.replace;

import org.springframework.beans.factory.support.MethodReplacer;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ReplaceDog implements MethodReplacer {
	@Override
	public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
		System.out.println("Hello, I am a white dog...");

		Arrays.stream(args).forEach(str -> System.out.println("参数:" + str));
		return obj;
	}
}
```

配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="dogReplaceMethod" class="com.mashibing.methodOverrides.replace.ReplaceDog"/>
    <bean id="originalDogReplaceMethod" class="com.mashibing.methodOverrides.replace.OriginalDog">
        <replaced-method name="sayHello" replacer="dogReplaceMethod">
            <arg-type match="java.lang.String"></arg-type>
        </replaced-method>
    </bean>
</beans>
```

Test.java

```java
package com.mashibing;

import com.mashibing.methodOverrides.lookup.FruitPlate;
import com.mashibing.methodOverrides.replace.OriginalDog;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestMethodOverrides {

    public static void main(String[] args) {
        ApplicationContext app = new ClassPathXmlApplicationContext("bean.xml");
        OriginalDog originalDogReplaceMethod = app.getBean("originalDogReplaceMethod", OriginalDog.class);
        originalDogReplaceMethod.sayHello("结果被替换");
    }
}

```

## 12、bean supplier的应用

CreateSupplier.java

```java
package com.mashibing.supplier;

public class CreateSupplier {

    public static User createUser(){
        return new User("张三");
    }
}

```

User.java

```java
package com.mashibing.supplier;

public class User {

    private String username;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }
}

```

UserBeanFactoryPostProcessor.java

```java
package com.mashibing.supplier;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

public class UserBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition user = beanFactory.getBeanDefinition("user");
        GenericBeanDefinition beanDefinition = (GenericBeanDefinition) user;
        beanDefinition.setInstanceSupplier(CreateSupplier::createUser);
        beanDefinition.setBeanClass(User.class);
    }
}
```

xml

```xml
  <bean id="user" class="com.mashibing.supplier.User"></bean>
  <bean class="com.mashibing.supplier.UserBeanFactoryPostProcessor"></bean>
```

Test.java

```java
MyClassPathXmlApplicationContext ac = new MyClassPathXmlApplicationContext("applicationContext.xml");
        User bean = ac.getBean(User.class);
        System.out.println(bean.getUsername());
```

## 13、resolveBeforeInstantiation的调用执行

​		此方法存在的意义在于给BeanPostProcessor的实现子类一个机会去生成代理对象来替代对象。

BeforeInstantiation.java

```java
package com.mashibing.resolveBeforeInstantiation;

public class BeforeInstantiation {

    public void doSomeThing(){
        System.out.println("执行do some thing ...");
    }
}

```

MyMethodInterceptor.java

```java
package com.mashibing.resolveBeforeInstantiation;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("目标方法前："+method);
        Object o1 = methodProxy.invokeSuper(o, objects);
        System.out.println("目标方法后："+method);
        return o1;
    }
}

```

MyInstantiationAwareBeanPostProcessor.java

```
package com.mashibing.resolveBeforeInstantiation;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;

public class MyInstantiationAwareBeanPostProcessor  implements InstantiationAwareBeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.print("beanName:"+beanName+"执行..postProcessBeforeInitialization\n");

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.print("beanName:"+beanName+"执行..postProcessAfterInitialization\n");

        return bean;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        System.out.print("beanName:"+beanName+"执行..postProcessAfterInstantiation\n");

        return false;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        System.out.print("beanName:"+beanName+"执行..postProcessBeforeInstantiation\n");
        //利用 其 生成动态代理
        if(beanClass==BeforeInstantiation.class){
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(beanClass);
            enhancer.setCallback(new MyMethodInterceptor());
            BeforeInstantiation beforeInstantiation = (BeforeInstantiation)enhancer.create();
            System.out.print("返回动态代理\n");
            return beforeInstantiation;
        }
        return null;
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        System.out.print("beanName:"+beanName+"执行..postProcessProperties\n");
        return pvs;
    }
}
```

Test.java

```java
package com.mashibing.resolveBeforeInstantiation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("resolveBeforeInstantiation.xml");
        BeforeInstantiation bean = ac.getBean(BeforeInstantiation.class);
        bean.doSomeThing();
    }
}

```

xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="beforeInstantiation" class="com.mashibing.resolveBeforeInstantiation.BeforeInstantiation"></bean>
    <bean id="myInstantiationAwareBeanPostProcessor" class="com.mashibing.resolveBeforeInstantiation.MyInstantiationAwareBeanPostProcessor"></bean>
</beans>
```

## 14、通过factoryMethod实例化对象

Person.java

```java
package com.mashibing.factoryMethod;

public class Person {
    private int id;
    private String name;
    private int age;
    private String gender;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}
```

PersonInstanceFactory.java

```java
package com.mashibing.factoryMethod;

public class PersonInstanceFactory {
    public Person getPerson(String name){
        Person person = new Person();
        person.setId(1);
        person.setName(name);
        return person;
    }
}
```

PersonStaticFactory.java

```java
package com.mashibing.factoryMethod;

public class PersonStaticFactory {

    public static Person getPerson(String name){
        Person person = new Person();
        person.setId(1);
        person.setName(name);
        return person;
    }
}
```

factoryMethod.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="person5" class="com.mashibing.factoryMethod.PersonStaticFactory" factory-method="getPerson">
        <!--constructor-arg：可以为方法指定参数-->
        <constructor-arg value="lisi"></constructor-arg>
    </bean>
    <bean id="personInstanceFactory" class="com.mashibing.factoryMethod.PersonInstanceFactory"></bean>
    <!--
    factory-bean:指定使用哪个工厂实例
    factory-method:指定使用哪个工厂实例的方法
    -->
    <bean id="person6" class="com.mashibing.factoryMethod.Person" factory-bean="personInstanceFactory" factory-method="getPerson">
        <constructor-arg value="wangwu"></constructor-arg>
    </bean>
</beans>
```

Test.java

```java
package com.mashibing;

import com.mashibing.config.MyPropertySource;
import com.mashibing.selfConverter.Student;
import com.mashibing.supplier.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.convert.ConversionService;

public class Test {

    public static void main(String[] args) {
        MyClassPathXmlApplicationContext ac = new MyClassPathXmlApplicationContext("factoryMethod.xml");

    }
}
```

## 15、修改源码验证为什么需要三级缓存

​		通过课上的讲解我们知道了，如果没有动态代理的话，我们其实只需要二级缓存就足以解决循环依赖问题，因为有了动态代理，所以必须要使用三级缓存来解决此问题，下面来修改源代码，验证只使用二级缓存是否能解决循环依赖的问题。

1、修改doCreatenBean方法：

```java
if (earlySingletonExposure) {
			if (logger.isTraceEnabled()) {
				logger.trace("Eagerly caching bean '" + beanName +
						"' to allow for resolving potential circular references");
			}
			// 为避免后期循环依赖，可以在bean初始化完成前将创建实例的ObjectFactory加入工厂
			//addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, mbd, bean));

			//只保留二级缓存，不向三级缓存中存放对象
			earlySingletonObjects.put(beanName,bean);
			registeredSingletons.add(beanName);

		}
```

2、修改getSingleton方法

```java
@Nullable
	protected Object getSingleton(String beanName, boolean allowEarlyReference) {
		Object singletonObject = this.singletonObjects.get(beanName);
		if (singletonObject == null && isSingletonCurrentlyInCreation(beanName)) {
				synchronized (this.singletonObjects) {
					singletonObject = this.earlySingletonObjects.get(beanName);
					return singletonObject;
				}
			}
		return singletonObject != null ? singletonObject:null;
	}
```

3、修改三级缓存的访问权限

```java
public final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);
public final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);
public final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>(16);
public final Set<String> registeredSingletons = new LinkedHashSet<>(256);
```

