##
##临界知识
###配置策略
[](https://logging.apache.org/log4j/2.x/manual/configuration.html#AutomaticConfiguration)
```
Log4j will inspect the "log4j2.configurationFile" system property and, if set, will attempt to load the configuration using the ConfigurationFactory that matches the file extension. Note that this is not restricted to a location on the local file system and may contain a URL.
If no system property is set the properties ConfigurationFactory will look for log4j2-test.properties in the classpath.
If no such file is found the YAML ConfigurationFactory will look for log4j2-test.yaml or log4j2-test.yml in the classpath.
If no such file is found the JSON ConfigurationFactory will look for log4j2-test.json or log4j2-test.jsn in the classpath.
If no such file is found the XML ConfigurationFactory will look for log4j2-test.xml in the classpath.
If a test file cannot be located the properties ConfigurationFactory will look for log4j2.properties on the classpath.
If a properties file cannot be located the YAML ConfigurationFactory will look for log4j2.yaml or log4j2.yml on the classpath.
If a YAML file cannot be located the JSON ConfigurationFactory will look for log4j2.json or log4j2.jsn on the classpath.
If a JSON file cannot be located the XML ConfigurationFactory will try to locate log4j2.xml on the classpath.
If no configuration file could be located the DefaultConfiguration will be used. This will cause logging output to go to the console.
```
###依赖库关系
log4j-api
log4j-core
```
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-api</artifactId>
    <version>2.14.1</version>
  </dependency>
  <dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.14.1</version>
  </dependency>

Log4j 2 is broken up in an API and an implementation (core), 
where the API provides the interface that applications should code to. 
Strictly speaking Log4j core is only needed at runtime and not at compile time.

```

```
If existing components use Log4j 1.x and you want to have this logging routed to Log4j 2, then remove any log4j 1.x dependencies and add the following.
<groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-1.2-api</artifactId>

```

```
If existing components use Apache Commons Logging 1.x and you want to have this logging routed to Log4j 2, 
then add the following but do not remove any Commons Logging 1.x dependencies.
<groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-jcl</artifactId>
```

```
If existing components use SLF4J and you want to have this logging routed to Log4j 2, then add the following but do not remove any SLF4J dependencies.
 <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-slf4j-impl</artifactId>

```

```
If existing components use Java Util Logging and you want to have this logging routed to Log4j 2, then add the following.
<groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-jul</artifactId>

```

```asp
The Log4j 2 to SLF4J Adapter allows applications coded to the Log4j 2 API to be routed to SLF4J. 
Use of this adapter may cause some loss of performance as the Log4j 2 Messages must be formatted before they can be passed to SLF4J. The SLF4J Bridge must NOT be on the class path when this is in use.
<groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-to-slf4j</artifactId>

```
