ConfigurationClassPostProcessor导入配置逻辑@Import
##1.需求:
通过@Import导入配置类
##2.方案:
扫描类注解是否有@Import,并根据类型进行不同方式的注入beandefinition逻辑,
支持ImportBeanDefinitionRegistrar,ImportSelector,普通pojo的注入
##3.实现:
1.获取候选组件,即被@component注解的类,并包装为ConfigurationClass,获取其中的@import信息进行处理
2.对候选类中@Import信息不为空的申明类,进行processImports处理

3.申明类为DeferredImportSelector类型,缓存在deferredImportSelectors map中延时处理
4.申明类为ImportSelector类型,通过ImportSelector.selectImports获取importClassNames,重复第2步,递归处理

5.申明类为ImportBeanDefinitionRegistrar,实例化申明类,并缓存在ConfigurationClass对象的importBeanDefinitionRegistrars中用于后续创建beandefinition
6.申明类非上述三种,当做@component候选类的逻辑处理processConfigurationClass,最后会缓存在configurationClasses中用于后续创建beandefinition

7.解析完候选组件后,开始处理deferredImportSelectorHandler缓存中的申明类,对申明类重复第2步,递归处理

8.对于configurationClasses缓存map中的类,将其中被@import导入的类进行registerBeanDefinition,registerBeanDefinitionForImportedConfigurationClass
9.对于configurationClasses缓存map中的类,获取ConfigurationClass对象的ImportBeanDefinitionRegistrar缓存并进行registrar.registerBeanDefinitions处理


##类型:
包裹类:ConfigurationClass,BeanMethod,BeanDefinitionHolder,AnnotatedGenericBeanDefinition
工具类:AnnotationConfigUtils
策略类:BeanNameGenerator,ConditionEvaluator
环境类:ConditionContextImpl

##设计模式:
##数据结构:
ArrayDeque
LinkedHashMap
##语法:
1.lamda:  private static final Predicate<String> DEFAULT_EXCLUSION_FILTER = className ->
			(className.startsWith("java.lang.annotation.") || className.startsWith("org.springframework.stereotype."));

2.接口default方法:default void registerBeanDefinitions

##命名:
DefaultPropertySourceFactory
MetadataReaderFactory
processImports
ParserStrategyUtils
deferredImportSelectors
importCandidates
ImportBeanDefinitionRegistrar
AnnotationScopeMetadataResolver
ConditionEvaluator