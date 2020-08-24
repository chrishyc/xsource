被@Configuration注解的类的注入过程,主要逻辑位于ConfigurationClassPostProcessor.postProcessBeanDefinitionRegistry
##1.需求:用于加载有@Configuration注解的bean
##2.方案:复写BeanFactoryPostProcessor.postProcessBeanFactory方法
加载@Configuration类,并分析加载该类上的注解对象
##3.实现:
###1.执行过程:

注入ConfigurationClassPostProcessor.BeanDefinition===========================================
SpringApplication.run->
SpringApplication.prepareContext->
SpringApplication.load->
SpringApplication.createBeanDefinitionLoader->
BeanDefinitionLoader.new BeanDefinitionLoader()->
AnnotatedBeanDefinitionReader.new AnnotatedBeanDefinitionReader()->
AnnotatedBeanDefinitionReader,AnnotationConfigUtils.registerAnnotationConfigProcessors()->
AnnotationConfigUtils,new RootBeanDefinition(ConfigurationClassPostProcessor.class)->
AnnotationConfigUtils,registry.registerBeanDefinition->

执行postProcessBeanDefinitionRegistry========================================================
SpringApplication.refreshContext->
AbstractApplicationContext.refresh()->
AbstractApplicationContext.invokeBeanFactoryPostProcessors()->
PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors()->
ConfigurationClassPostProcessor.postProcessBeanDefinitionRegistry()->

解析被@Configuration注解类========================================================================
ConfigurationClassParser.parse()->
ConfigurationClassParser.processConfigurationClass()->
ConfigurationClassParser.doProcessConfigurationClass()->

解析ComponentScan注解,获取指定包下@Component注解Bean并将BeanDefinition注入容器=======================
ComponentScanAnnotationParser.parse()->
ClassPathBeanDefinitionScanner.doScan()->
ClassPathScanningCandidateComponentProvider.scanCandidateComponents()->
PathMatchingResourcePatternResolver.getResources()->
ClassPathScanningCandidateComponentProvider.isCandidateComponent()->
TypeFilter.match()->
ScannedGenericBeanDefinition.new ScannedGenericBeanDefinition()->
ClassPathBeanDefinitionScanner.registerBeanDefinition()->
ConfigurationClassParser,configurationClasses.put(configClass, configClass)->

解析@processImports并缓存对应处理器==============================================================
ConfigurationClassParser.processImports()->

调用处理器Selector==============================================================================
ConfigurationClassParser,ImportSelector.selectImports
ConfigurationClassParser,deferredImportSelectorHandler.process()->

调用Registrar处理器注入BeanDefinition===========================================================
ConfigurationClassPostProcessor,ConfigurationClassBeanDefinitionReader.loadBeanDefinitions->
ConfigurationClassPostProcessor,ConfigurationClassBeanDefinitionReader.loadBeanDefinitionsForConfigurationClass->
ConfigurationClassPostProcessor,ConfigurationClassBeanDefinitionReader.loadBeanDefinitionsFromRegistrars->
ImportBeanDefinitionRegistrar.registerBeanDefinitions()->

对上述BeanDefinition生成实例对象==================================================================
AbstractApplicationContext.finishBeanFactoryInitialization()->


###2.关键:
注入ConfigurationClassPostProcessor清单
invoke ConfigurationClassPostProcessor主逻辑processConfigBeanDefinitions调用
过滤获取@Configuration注解类checkConfigurationClassCandidate
解析@Configuration注解所在类的其他注解信息,ConfigurationClassParser
将其他注解信息注入ConfigurationClassBeanDefinitionReader.loadBeanDefinitions
对PostProcessor进行实例注入,普通清单将在finishBeanFactoryInitialization进行实例注入

启动类:ConfigurationClassPostProcessor
策略类:AnnotationBeanNameGenerator,SourceExtractor,BeanNameGenerator
监测类:ProblemReporter
资源加载类:ResourceLoader
工具类:ConfigurationClassBeanDefinitionReader

###3.设计模式:
###4.数据结构:
HashSet
ConcurrentHashMap
###5.语法:
static final
@Nullable
private final
private static class
###6.命名:
PostProcessor
ResourceLoaderAware
PriorityOrdered
EnvironmentAware
NameGenerator
FullyQualified
PassThroughSourceExtractor
FailFast
CachingMetadataReaderFactory
