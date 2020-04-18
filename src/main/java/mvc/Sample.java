package mvc;

import org.junit.Test;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class Sample {
    /**父子webApplicationContext
     * 1.{@link org.springframework.web.context.ContextLoaderListener#contextInitialized}
     * 会监听ServletContext初始化回调，查看是否有contextClass，有则加载。此处的contextClass为父webApplicationContext
     *
     * 2.{@link org.springframework.web.servlet.DispatcherServlet}父类
     * {@link org.springframework.web.servlet.FrameworkServlet#createWebApplicationContext}会生成一个
     * 子类webApplicationContext，即XmlWebApplicationContext
     *
     */
    @Test
    public void testInitWebContext(){
    
    }
    
    /**
     * {@link org.springframework.web.servlet.config.AnnotationDrivenBeanDefinitionParser#parse(Element, ParserContext)}
     * 开启mvc注解驱动后，会配置
     * RequestMappingHandlerMapping
     * conversionService(default:FormattingConversionServiceFactoryBean)
     * validator(default:OptionalValidatorFactoryBean)
     * ConfigurableWebBindingInitializer
     * RequestMappingHandlerAdapter
     * ExceptionHandlerExceptionResolver
     * ResponseStatusExceptionResolver
     * DefaultHandlerExceptionResolver
     */
    @Test
    public void testMVCBeanDefinitionParser(){
    
    }
    
    /**
     * mvc 注册component扫描器后会解析注解，默认在
     * {@link org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider#registerDefaultFilters}
     * 会注册@Component，只要注解上有@Component,都会被解析生成对应的Bean实例
     */
    public void testComponentScan(){
    
    }
}
