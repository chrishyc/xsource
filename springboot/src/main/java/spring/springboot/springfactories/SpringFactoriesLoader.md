SpringFactoriesLoader
##1.需求
使用配置文件实现spring自动配置与加载.当项目启动时,会读取每个jar包下spring项目配置文件META-INF/spring.factories并缓存在map中,可以使用对应的key获取map中的value,并将value注入到beanfacatory用于后续操作.
其中map的key为注解类名/接口名,value为工具类名集合,例如:
```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,\
org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,\
org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration,\

```
##2.方案
sun.misc.Launcher$AppClassLoader类加载器加载每个jar中META-INF/spring.factories文件,并将结果缓存在
Map<ClassLoader, MultiValueMap<String, String>>中

##3.实现
1.关键:
```
try {
			Enumeration<URL> urls = (classLoader != null ?
					classLoader.getResources(FACTORIES_RESOURCE_LOCATION) :
					ClassLoader.getSystemResources(FACTORIES_RESOURCE_LOCATION));
			result = new LinkedMultiValueMap<>();
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				UrlResource resource = new UrlResource(url);
				Properties properties = PropertiesLoaderUtils.loadProperties(resource);
				for (Map.Entry<?, ?> entry : properties.entrySet()) {
					String factoryTypeName = ((String) entry.getKey()).trim();
					for (String factoryImplementationName : StringUtils.commaDelimitedListToStringArray((String) entry.getValue())) {
						result.add(factoryTypeName, factoryImplementationName.trim());
					}
				}
			}
			cache.put(classLoader, result);
```
2.类型:
工具类:SpringFactoriesLoader,PropertiesLoaderUtils

3.设计模型:NA
空模式:
static final EmptyEnumeration<Object> EMPTY_ENUMERATION = new EmptyEnumeration<>();
```
private static class EmptyEnumeration<E> implements Enumeration<E> {
        static final EmptyEnumeration<Object> EMPTY_ENUMERATION
            = new EmptyEnumeration<>();

        public boolean hasMoreElements() { return false; }
        public E nextElement() { throw new NoSuchElementException(); }
    }
```



4.数据结构:
ConcurrentReferenceHashMap
MultiValueMap,LinkedMultiValueMap
Properties
Enumeration,CompoundEnumeration

5.语法:
final类:final class SpringFactoriesLoader
私有构造器:private SpringFactoriesLoader()
方法泛型:List<T>
注解:@Nullable

6.命名:
SpringFactoriesLoader
instantiateFactory
accessibleConstructor
cache
