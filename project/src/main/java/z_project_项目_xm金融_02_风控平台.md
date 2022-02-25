#redis 消息队列
#redis双写
#redis lua,事务
#redis mysql双写
#二级缓存
理清楚二者过期时间,二者执行顺序,guava原理,缓存一致性
从内存guava,redis,数据库中获取
[z_java_03_guava_cache.md],1d,groupid,processid,version
[redis]set,expire,1d
[mysql]get
[z_01_分布式_临界知识_多级缓存.md]
#双写缓存一致性,guava缓存失效
zookeeper+Cache.invalidate
#雪崩,击穿
[](z_java_03_guava_cache.md)
##redis分布式锁实现分布式定时任务
[z_01_分布式_临界知识_分布式锁.md]
redission, redlock
##使用Redis缓存推荐码、短链接，加载热点数据等热数据,常用redis操作
[z_04_分布式_redis_01_常见问题_常见应用场景_redis分布式锁_原子操作_公司集群_项目常用_简单限流.md]五大操作
HSET KEY field value
field = mifi id_url,
value = mifi id_code,



#延时队列
[z_04_分布式_redis_01_常见问题_常见应用场景_redis分布式锁_原子操作_公司集群_项目常用_简单限流.md]sorted_set
#id-generator冲突
雪花算法,两个服务都是一套
冲突了
timestamp,31
machineTag,不一样,我们machineTag给了5位，但是我们从100开始,部分和id冲突了
step不一样
冲突
#日志脱敏字节码错误
##选型对比,为啥用asm
动态代理vs字节码插桩vs日志代理
###编译器注解处理器
[z_01_编译期注解.md]
需要使用lombok注解,对于第三方pojo,如thrift pojo没有办法进行脱敏
###动态代理
画图
[z_03_动态代理.md]
动态代理生成代理类,cglib,jdk proxy,需要管理代理类,但是已有的实体类pojo都是new方式创建,不会走代理类逻辑
###字节码插桩
[z_02_字节码插桩_asm.md]
###aop拦截日志打印
所有的打印都做了切面,影响范围太广,需要逻辑判断输出的打印日志是否含有敏感日志,所有日志打印执行时间增长
##有什么难点?字节码脱敏常量池错误
[z_02_字节码插桩_asm.md]
![](.z_02_字节码插桩_asm_images/4d561234.png)
1.arthas 反编译字节码class文件,发现tostring中 throw new OutOf异常,看不到tostring的字节码
2.对比正常和异常的class文件,发现一个tostring只有属性变量,一个有实例方法,猜测是实例方法的问题
3.本地复现,发现实例方法果然报错,拿到日志脱敏源码,只对属性变量脱敏,并没有处理方法,即使没脱敏也不应该报错
4.控制变量,缩小范围,最后发现复写MethodVisitor时,visitVarInsn方法过滤了ALOAD_0,导致实例方法获取不到this,invoke出错,而访问visitFieldInsn时主动ALOAD_0,所以没出错
5.之前考虑为了避免tostring调用实例方法,除去了this指针,紧急使用lombok的@ToString(exclude = "map")去除了出错的map上线
6.后来恢复this指针,做了长期测试,观察了一周后上线,避免脱敏导致的业务报错
