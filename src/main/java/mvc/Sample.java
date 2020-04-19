package mvc;

import mvc.controller.ParamMappingController;
import mvc.controller.ReturnTypeController;
import org.junit.Test;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.method.support.*;

public class Sample {
    /**
     * 父子webApplicationContext
     * 1.{@link org.springframework.web.context.ContextLoaderListener#contextInitialized}
     * 会监听ServletContext初始化回调，查看是否有contextClass，有则加载。此处的contextClass为父webApplicationContext
     * <p>
     * 2.{@link org.springframework.web.servlet.DispatcherServlet}父类
     * {@link org.springframework.web.servlet.FrameworkServlet#createWebApplicationContext}会生成一个
     * 子类webApplicationContext，即XmlWebApplicationContext
     */
    @Test
    public void testInitWebContext() {
    
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
    public void testMVCBeanDefinitionParser() {
    
    }
    
    /**
     * mvc 注册component扫描器后会解析注解，默认在
     * {@link org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider#registerDefaultFilters}
     * 会注册@Component，只要注解上有@Component,都会被解析生成对应的Bean实例
     */
    @Test
    public void testComponentScan() {
    
    }
    
    /**
     * 初始化过程:
     * 1.spring mvc默认HandlerMapping为RequestMappingHandlerMapping
     * 2.注册RequestMappingHandlerMapping时机为
     * {@link org.springframework.web.servlet.config.AnnotationDrivenBeanDefinitionParser#parse(Element, ParserContext)}
     * 3.生成mapping时机:当RequestMappingHandlerMapping初始化生成实例且注入后，会调用RequestMappingHandlerMapping的afterPropertiesSet
     * 去生成对应的mapping
     * 4.获取bean容器所有bean去判断bean是否是handler,{@link org.springframework.web.servlet.handler.AbstractHandlerMethodMapping#processCandidateBean(String)}
     * ```
     * protected boolean isHandler(Class<?> beanType) {
     * return (AnnotatedElementUtils.hasAnnotation(beanType, Controller.class) ||
     * AnnotatedElementUtils.hasAnnotation(beanType, RequestMapping.class));
     * }
     * ```
     * <p>
     * 5.RequestMappingHandlerMapping.createRequestMappingInfo扫描含有RequestMapping的方法
     * 6.RequestMappingHandlerMapping.registerHandlerMethod完成mapping生成过程.
     * 7.AbstractApplicationContext.finishRefresh时发起publishEvent,导致DispatcherServlet刷新初始化initStrategies,此时会初始化
     * DispatcherServlet的handlerMapping,DispatcherServlet.initHandlerMappings
     * <p>
     * <p>
     * 请求过程:
     * 8.请求到来时，会进入DispatcherServlet.doDispatch，决定对应的handler,会进入DispatcherServlet.getHandler
     * 9.进入AbstractHandlerMethodMapping.lookupHandlerMethod获取对应的handler
     * 10.AbstractHandlerMapping.getHandler组装对应的执行链HandlerExecutionChain
     * 11.会进入DispatcherServlet.doDispatch中组装对应的HandlerAdapter，DispatcherServlet.getHandlerAdapter
     * 对应的是RequestMappingHandlerAdapter
     * 12.RequestMappingHandlerAdapter.invokeHandlerMethod执行最后的方法逻辑
     */
    @Test
    public void testHandlerMapping() {
    
    }
    
    /**
     * HandlerMethodArgumentResolver
     * HandlerMethodReturnValueHandler
     * WebDataBinderFactory
     * ParameterNameDiscoverer
     * {@link RequestMappingHandlerAdapter#afterPropertiesSet()}
     * 获取默认method参数解析器.getDefaultArgumentResolvers
     * 获取默认method参数绑定器.getDefaultInitBinderArgumentResolvers
     * 获取默认method返回值处理器.getDefaultReturnValueHandlers
     * <p>
     * 1.如何确定需要绑定哪些参数?
     * <p>
     * 2.这些参数如何绑定的?
     * <p>
     *
     * {@link HandlerMethodArgumentResolverComposite#getArgumentResolver}
     * 1.参数为pojo,{@link ParamMappingController#pojo}
     * 2.参数为基本类型,{@link ParamMappingController#primitiveType}
     * 3.参数为基本类型+@RequestParam，{@link ParamMappingController#primitiveTypeAnnotation}
     */
    @Test
    public void testMethodParam() {
    
    }
    
    /**
     * Represents a model and view returned by a handler
     * {@link org.springframework.web.servlet.HandlerAdapter#handle(HttpServletRequest, HttpServletResponse, Object)}
     * <p>
     * DispatcherServlet.doDispatch
     * // Actually invoke the handler. handlerAdapter处理完handler逻辑会返回一个ModelAndView
     * mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
     * <p>
     * 1.什么时候返回ModelAndView？什么时候返回其他结果?
     * 2.返回void怎么处理?
     * <p>
     * 1.有返回值且返回类型为ModelAndView，{@link ReturnTypeController#returnModelAndView()}
     * 2.返回void时{@link ReturnTypeController#returnVoid()}
     * 3.返回string时{@link ReturnTypeController#returnString()}
     * 4.返回responseBody时{@link ReturnTypeController#returnResponseBody}
     */
    @Test
    public void testReturn() {
    
    }
    
    
}
