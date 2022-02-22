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

#推荐活动
![](.z_project_项目_小米_01_小米金融海外贷超平台_images/638cab9e.png)
1.分享推荐码
2.绑定推荐码
![](.z_project_项目_小米_01_小米金融海外贷超平台_images/3fe7dce7.png)
3.授信后查看推荐关系是否存在,发放授信奖励,先存奖励表status=create,再请求rpc,再更新status=new
4.贷款后查看推荐关系是否存在,发送贷款奖励,先存奖励表status=create,再请求rpc,再更新status=new
5.人工补偿,查看推荐关系是否存在,发送授信/贷款奖励,先存奖励表status=create,再请求rpc,再更新status=new
##Mysql实现推荐奖励请求的幂等性
###为啥要幂等?
推荐活动的奖励表记录在贷超,发放奖励由支付部门的服务发放到用户账号,跨服务请求
###幂等的唯一键
mifi+referRelationId+type(奖励类型)
insert into  select from dual where not exists( select id )
```asp
insert ignore into `Test` select 'aa' , 'cao' from dual where not exists(select 1 from Test where `first_name`='aa' and `second_name`='bb');
```
###数据库幂等性
状态机+补偿性定时任务
1.insert into  status=CREATE
2.rpc request抽奖,之所以请求,因为可以由运营配置
3.rpc 请求分配金额,此时只是记录用户分配的金额,并未真正到账
4.update status=NEW
##通过事件表以及定时任务补偿机制保证了奖励更新的柔性一致性
###rocketMq异步请求一次
###定时任务扫描事件表
更新状态机
##redis分布式锁实现分布式定时任务
[z_01_分布式_临界知识_分布式锁.md]
redission, redlock
##使用Redis缓存推荐码、短链接等热数据
HSET KEY field value
field = mifi id_url,
value = mifi id_code,
##使用短网址方案精简推荐活动URL分享
#实现activiti风控审批流的自由跳转
#通过Hbase统计授信支用的每日报表
