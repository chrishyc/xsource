##对象模型
##临界知识
###适配器模式
Log
LogFactoryImpl.discoverLogImplementation
###配置机制
```
public static LogFactory getFactory()
                             throws LogConfigurationException
Construct (if necessary) and return a LogFactory instance, using the following ordered lookup procedure to determine the name of the implementation class to be loaded.
1.The org.apache.commons.logging.LogFactory system property.
2.The JDK 1.3 Service Discovery mechanism
3.Use the properties file commons-logging.properties file, if found in the class path of this class. The configuration file is in standard java.util.Properties format and contains the fully qualified name of the implementation class with the key being the system property defined above.
4.Fall back to a default implementation class (org.apache.commons.logging.impl.LogFactoryImpl).
```
