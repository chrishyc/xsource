##临界知识
```asp
二阶段提交协议，不仅仅是协议，也是一种非常经典的思想。二阶段提交在达成提交操 作共识的算法中应用广泛，比如 XA 协议、TCC、Paxos、Raft 等   

幂等性，是指同一操作对同一系统的任意多次执行，所产生的影响均与一次执行的影响 相同，不会因为多次执行而产生副作用。常见的实现方法有 Token、索引等。
它的本质 是通过唯一标识，标记同一操作的方式，来消除多次执行的副作用  

Paxos、Raft 等强一致性算法，也采用了二阶段提交操作，在“提交请求阶段”，只要 大多数节点确认就可以，而具有 ACID 特性的事务，则要求全部节点确认可以。
所以可 以将具有 ACID 特性的操作，理解为最强的一致性
```
补偿机制,极低概率人工补偿vs研发成本
[](https://zhuanlan.zhihu.com/p/387487859)
[DTM](https://github.com/dtm-labs/dtm)
[seata](https://github.com/seata/seata)
[](https://www.cnblogs.com/jajian/p/10014145.html)
##二阶段提交协议(2PC,强一致)

###对象
协调者：事务管理器
客户端：资源管理器
###过程
```asp
在第一个阶段，每个参与者投票表决事务是放弃还是提交。一旦参与者投票 要求提交事务，那么就不允许放弃事务
在提交请求阶段，需要预留资源，在资源预留期间，其他人不能操作(比如，XA 在第一 阶段会将相关资源锁定);
数据库是独立的系统。
因为上面这两点，我们无法根据业务特点弹性地调整锁的粒度，而这些都会影响数据库的并 发性能
```
1.确认
2.提交/回滚
![](.z_01_分布式_临界知识_分布式事务(consistency)_二阶段提交_TCC_最强一致性_images/c664477e.png)
###问题
```asp
1阶段超时/失败:TM回滚
2阶段超时/失败:TM重试
```
![](.z_01_分布式_临界知识_分布式事务(consistency)_二阶段提交_TCC_最强一致性_images/24476356.png)
1.只有TM有超时机制
###在一次面试中被问到一个问题，如果一个transaction的rollback失败了，应该怎么办？
1. 直接重试；
2. 触发告警，然后人工根据日志记录进行修复；
3. 设计异步回滚流程，也就是说在一个异步流程中对账、回滚，避免因重试耗时而拖慢整体性能。
##三阶段提交协议(3PC,强一致)
[](https://juejin.cn/post/6844903621495111688)
[](https://time.geekbang.org/column/article/144970)
###变化
1.RM超时机制,TM超时机制,降低锁定资源的时长
2.增加准备阶段,降低锁定资源的时长
###过程
![](.z_01_分布式_临界知识_分布式事务(consistency)_二阶段提交_TCC_最强一致性_images/f88f44a9.png)
###问题
降低锁定资源的时长,2pc的问题都没有解决
![](.z_01_分布式_临界知识_分布式事务(consistency)_二阶段提交_TCC_最强一致性_images/24476356.png)
##基于消息的最终一致性方法(最终一致)
2PC 和 3PC 核心思想均是以集中式的方式实现分布式事务，这两种方法都存在两个共同的缺点，
一是，同步执行，性能差；
二是，数据不一致问题

###rocketmq(半消息,消息有状态)
1.发半消息到rocketmq
2.本地执行sql
3.rocketmq定时轮询表状态
[](https://juejin.cn/post/6844903951448408071)
![](.z_01_分布式_临界知识_分布式事务(consistency)_二阶段提交_TCC_最强一致性_images/c147834e.png)
```asp
可以不用消息队列吗?消息队列的作用?
发送到消息队列就代表支付成功了,不用消息队列需要不断重试,业务端来维护
消息队列解耦生产端和消费端,可能多个消费端
```
![](.z_01_分布式_临界知识_分布式事务(consistency)_二阶段提交_TCC_最强一致性_images/a8ea5864.png)
```asp
rocketmq重复发送多次后进入死信队列,可以人工介入
```
###消息队列(定时任务+本地事件表,数据库事件有状态)
1.执行sql,插入事件和事件初始状态
2.定时轮询事件表,发送消息到mq
3.mq返回ack后更新事件表状态
![](.z_01_分布式_临界知识_分布式事务(consistency)_二阶段提交_TCC_最强一致性_images/eb13a1ce.png)
```asp
幂等性:消费端通过消息时间id的唯一键约束,避免消息重复消费
```
##TCC(最终一致性)
seata
TCC 是 Try(预留)、Confirm(确认,失败重试->人工补偿)、Cancel(撤销,失败重试->人工补偿) 
TCC 不依赖于数据库的事务，而是在业务中实现了分布式事务，这样能减轻数据库 的压力，但对业务代码的入侵性也更强，实现的复杂度也更高
不必对数据加全局锁，允许多个事务同时操作数据。
[](https://xie.infoq.cn/article/85444fc19f7c4bf770111f0d7)
[](https://juejin.cn/post/6969550286750744590)
[](https://www.codingapi.com/docs/txlcn-demo-springcloud/)
2pc从事务资源的角度利用强一致性来解决问题,
而tcc从业务角度来解决问题，把强一致性改成了最终一致性，大大提高了效率。但是tcc明显对代码造成了入侵，你原本只需要一个接口，就不得不拆分成三个接口来处理，对开发者的要求也会更高
```asp
@Slf4j
@Component
public class OrderTccActionImpl implements OrderTccAction{

    @Autowired
    private OrderMapper orderMapper;

    @Override
    @Transactional
    public boolean createOrder(BusinessActionContext businessActionContext, Order order) {
        order.setStatus(1);
        orderMapper.insert(order);
        log.info("创建订单：tcc一阶段try成功");
        return true;
    }

    @Override
    @Transactional
    public boolean commit(BusinessActionContext businessActionContext) {
        JSONObject jsonObject= (JSONObject) businessActionContext.getActionContext("order");
        Order order=new Order();
        BeanUtil.copyProperties(jsonObject,order);
        order.setStatus(0);
        orderMapper.update(order,new LambdaQueryWrapper<Order>().eq(Order::getOrderNumber,order.getOrderNumber()));
        log.info("创建订单：tcc二阶段commit成功");
        return true;
    }

    @Override
    @Transactional
    public boolean rollback(BusinessActionContext businessActionContext) {
        JSONObject jsonObject= (JSONObject) businessActionContext.getActionContext("order");
        Order order=new Order();
        BeanUtil.copyProperties(jsonObject,order);
        orderMapper.delete(new LambdaQueryWrapper<Order>().eq(Order::getOrderNumber,order.getOrderNumber()));
        log.info("创建订单：tcc二阶段回滚成功");
        return true;
    }
}

```

###幂等性
###空回滚
[](https://cloud.tencent.com/developer/article/1786144)
###悬挂

##最大努力通知
##saga
#seata
Seata 虽然是保证数据一致性的组件，但对于 ORM 框架并没有特殊的要求，像主流的Mybatis，Mybatis-Plus，Spring Data JPA, Hibernate等都支持。
这是因为ORM框架位于JDBC结构的上层，而 Seata 的 AT,XA 事务模式是对 JDBC 标准接口操作的拦截和增强
[](https://seata.io/zh-cn/docs/overview/what-is-seata.html)
![](.z_01_分布式_临界知识_分布式事务(consistency)_二阶段提交_TCC_最强一致性_images/ce3fbb5f.png)
![](.z_01_分布式_临界知识_分布式事务(consistency)_二阶段提交_TCC_最强一致性_images/8dabf03b.png)
![](.z_01_分布式_临界知识_分布式事务(consistency)_二阶段提交_TCC_最强一致性_images/2ad09d69.png)
##拓扑&原理
###RM
###TC
###TM
###全局事务XID(参与的RM服务中传递)
[](https://seata.io/zh-cn/docs/user/microservice.html)
```asp
String xid = RootContext.getXID();

1. 服务内部的事务传播
默认的，RootContext 的实现是基于 ThreadLocal 的，即 XID 绑定在当前线程上下文中
// 挂起（暂停）
String xid = RootContext.unbind();

// TODO: 运行在全局事务外的业务逻辑

// 恢复全局事务上下文
RootContext.bind(xid);

2.跨服务调用的事务传播
跨服务调用场景下的事务传播，本质上就是要把 XID 通过服务调用传递到服务提供方，并绑定到 RootContext 中去
```
###全局事务表(TC服务中)
###分支事务状态表(TC服务中)

##AT(automic transaction,优化版2PC/XA)
![](.z_01_分布式_临界知识_分布式事务(consistency)_二阶段提交_TCC_最强一致性_images/dd3491d5.png)
阶段一,在本地事务中，一并提交业务数据更新和相应回滚日志记录
![](.z_01_分布式_临界知识_分布式事务(consistency)_二阶段提交_TCC_最强一致性_images/5867a1ff.png)
阶段二提交,马上成功结束，自动 异步批量清理回滚日志
![](.z_01_分布式_临界知识_分布式事务(consistency)_二阶段提交_TCC_最强一致性_images/67e777f3.png)
阶段三回滚,通过回滚日志，自动 生成补偿操作，完成数据回滚
![](.z_01_分布式_临界知识_分布式事务(consistency)_二阶段提交_TCC_最强一致性_images/1bbf8199.png)
```asp
限制:
基于支持本地 ACID 事务的关系型数据库。
Java 应用，通过 JDBC 访问数据库
```
```asp
全局锁:位于TC
查询前镜像:根据解析得到的条件信息，生成查询语句，定位数据
查询后镜像:主键定位
undo log table: seata在本地数据库创建的undo log 表，把前后镜像数据以及业务 SQL 相关的信息组成一条回滚日志记录，插入到 UNDO_LOG 表中
```
```asp
CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
```
##TCC
[]
TCC 模式，不依赖于底层数据资源的事务支持
一阶段 prepare 行为：调用 自定义 的 prepare 逻辑。
二阶段 commit 行为：调用 自定义 的 commit 逻辑。
二阶段 rollback 行为：调用 自定义 的 rollback 逻辑
![](.z_01_分布式_临界知识_分布式事务(consistency)_二阶段提交_TCC_最强一致性_images/670960d8.png)
##SAGA
![](.z_01_分布式_临界知识_分布式事务(consistency)_二阶段提交_TCC_最强一致性_images/979185db.png)
```asp
适用场景：
业务流程长、业务流程多
参与者包含其它公司或遗留系统服务，无法提供 TCC 模式要求的三个接口
不保证隔离性
```
##XA
##混合模式AT&TCC
##高可用
#公司支付使用的分布式事务方案
团队使用本地消息表,emq,适合跨行转账
[](https://xiaomi-info.github.io/2020/01/02/distributed-transaction/)
