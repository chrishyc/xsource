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

##公司支付使用的分布式事务方案
团队使用本地消息表,emq,适合跨行转账
[](https://xiaomi-info.github.io/2020/01/02/distributed-transaction/)
