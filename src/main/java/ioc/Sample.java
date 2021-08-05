package ioc;

import ioc.beanwrapper.Car;
import ioc.cycle.TestBean;
import ioc.lazyinit.LazyInitBean;
import ioc.life.LifeBean;
import ioc.processor.importcandidate.MyComponent;
import ioc.processor.importcandidate.MyImportSelector;
import org.junit.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.PropertyValue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Sample {
  /**
   * -Dspring.profiles.active=CHRIS
   */
  @Test
  public void testClassPathXml() {
    ApplicationContext ac = new ClassPathXmlApplicationContext("cycleBeans.xml");
    TestBean testBean = ac.getBean(TestBean.class);
    System.out.println(testBean);
  }

  @Test
  public void testFileSystemXml() {
    ApplicationContext ac = new FileSystemXmlApplicationContext("file:/Users/chris/xsource/src/main/resources/applicationContext.xml");
  }

  /**
   * 1.ApplicationListener生成逻辑{@link AbstractApplicationContext#registerListeners()}
   * 2.ApplicationListener调用逻辑{@link AbstractApplicationContext#publishEvent(Object)}
   */
  @Test
  public void testAnnotationConfig() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    TestBean myComponent = ac.getBean(TestBean.class);
    System.out.println(myComponent);
//        MyImportSelector myImportSelector = ac.getBean(MyImportSelector.class);
//        System.out.println(myImportSelector);
  }

  @Test
  public void testAnnotationConfigScan() {
    ApplicationContext ac = new AnnotationConfigApplicationContext("ioc");
    MyComponent myComponent = ac.getBean(MyComponent.class);
    System.out.println(myComponent);
    MyImportSelector myImportSelector = ac.getBean(MyImportSelector.class);
    System.out.println(myImportSelector);
  }

  @Test
  public void testCycle() {
    ApplicationContext ac = new ClassPathXmlApplicationContext("cycleBeans.xml");
//        TestBean testBean = ac.getBean(TestBean.class);
//        System.out.println(testBean);
  }

  @Test
  public void testLazyInit() {
    ApplicationContext ac = new ClassPathXmlApplicationContext("lazyInit.xml");
    LazyInitBean lazyInitBean = ac.getBean(LazyInitBean.class);
    System.out.println();
  }

  /**
   * 1.生成invokeBeanFactoryPostProcessors
   * 2.生成registerBeanPostProcessors
   * 3.生成普通bean,finishBeanFactoryInitialization
   * <p>
   * 生成BeanFactoryPostProcessors时会调用已有BeanPostProcessors
   * 生成BeanPostProcessors时会调用已有BeanPostProcessors
   * 生成普通bean时会调用已有BeanPostProcessors
   * <p>
   * 普通bean:
   * 构造函数初始化
   * set注入
   * setApplicationContext
   * <p>
   * <p>
   * postProcessBeforeInitialization
   * afterPropertiesSet
   * postProcessAfterInitialization
   */
  @Test
  public void testProcessor() {
    ApplicationContext ac = new ClassPathXmlApplicationContext("processor.xml");
    TestBean testBean = ac.getBean(TestBean.class);
    System.out.println(testBean);
  }

  @Test
  public void testLife() {
    ApplicationContext ac = new ClassPathXmlApplicationContext("life.xml");
    LifeBean lifeBean = ac.getBean(LifeBean.class);
    System.out.println(lifeBean);
  }

  /**
   * {@link org.springframework.context.config.ContextNamespaceHandler}
   */
  @Test
  public void testXmlComponentScan() {
    ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
  }

  /**
   * {@link org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider#registerDefaultFilters()}
   * 注册@Component.class
   * <p>
   * {@link org.springframework.context.annotation.ClassPathBeanDefinitionScanner#doScan}
   */
  @Test
  public void testAnnotationComponentScan() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
  }

  /**
   * {@link org.springframework.beans.factory.support.BeanDefinitionReaderUtils}
   * <p>
   * if (isInnerBean) {
   * // Inner bean: generate identity hashcode suffix.
   * id = generatedBeanName + GENERATED_BEAN_NAME_SEPARATOR + ObjectUtils.getIdentityHexString(definition);
   * }
   * <p>
   * spring内部bean都会带#
   * 例如DefaultBeanFactoryPointcutAdvisor#0
   */
  @Test
  public void testInnerBean() {

  }

  /**
   * debug bean实例关键点
   * postProcessBeforeInitialization，
   * postProcessAfterInitialization，
   * postProcessBeforeInstantiation，
   * postProcessAfterInstantiation，
   * addSingletonFactory，
   * addSingleton
   * <p>
   * AbstractApplicationContext.refresh
   */
  @Test
  public void testDebugPoint() {

  }

  @Test
  public void testBeanWrapper() {
    Car car = new Car();
    BeanWrapper beanWrapperOfCar = PropertyAccessorFactory.forBeanPropertyAccess(car);

    PropertyValue brandValue = new PropertyValue("brand", 124);

    PropertyValue maxSpeedValue = new PropertyValue("maxSpeed", "333");
    PropertyValue priceValue = new PropertyValue("price", "324.0");

    beanWrapperOfCar.setPropertyValue(brandValue);
    beanWrapperOfCar.setPropertyValue(maxSpeedValue);
    beanWrapperOfCar.setPropertyValue(priceValue);
  }
}
