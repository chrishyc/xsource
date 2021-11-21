#临界知识
leader两阶段提交机制
leader全局事务id
leader事务提交队列
选举期间leader,follow拒绝服务,observer可读
#ZAB共识算法
zab和raft都是通过强领导者模型实现就多值达成共识的
##leader模式
能保证操作顺序性的，基于主备模式的原子广播协议，在 ZAB 中，写操作必须在主节点（比如节点 A）上执行。如果客户端访问的节点是备份节点（比如节点 B），它会将写请求转发给主节
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_初始选举_崩溃恢复_广播消息_images/4e060a2a.png)

##唯一ID事务标识符
zxid,事务标识符是 64 位的 long 型变量，有任期编号 epoch 和计数器 counter 两部分组成（为了形象和方便理解，我把 epoch 翻译成任期编号），格式为 ，高 32 位为任期编号，低 32 位为计数器
```asp
任期编号，就是创建提案时领导者的任期编号，需要你注意的是，当新领导者当选时，任期编号递增，计数器被设置为零。比如，前领导者的任期编号为 1，那么新领导者对应的任期编号将为 2。
计数器，就是具体标识提案的整数，需要你注意的是，每次领导者创建新的提案时，计数器将递增。比如，前一个提案对应的计数器值为 1，那么新的提案对应的计数器值将为 2。
为什么要设计的这么复杂呢？因为事务标识符必须按照顺序、唯一标识一个提案，也就是说，事务标识符必须是唯一的、递增的。
```
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_初始选举_崩溃恢复_广播消息_images/c78318df.png)

##过半机制
```asp
1.Leader收到请求之后，将它转换为一个proposal提议，并且为每个提议分配一个全局唯一递增的事务ID：zxid，然后把提议放入到一个FIFO的队列中，按照FIFO的策略发送给所有的Follower

2.Follower收到提议之后，以事务日志的形式写入到本地磁盘中，写入成功后返回ACK给Leader

3.Leader在收到超过半数的Follower的ACK之后，即可认为数据写入成功，就会发送commit命令给Follower告诉他们可以提交proposal了
```
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_初始选举_崩溃恢复_广播消息_images/7ab12cad.png)
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_事务zxid_选举_事务提交_事务回滚_会话事务_images/f7e32680.png)
当主节点接收到指定提案的“大多数”的确认响应后，该提案将处于提交状态（Committed），主节点会通知备份节点提交该提案
##顺序广播(leader节点给每个follow一个proposal队列,一个commit队列)
主节点提交提案是有顺序性的。主节点根据事务标识符大小，按照顺序提交提案，如果前一个提案未提交，此时主节点是不会提交后一个提案的。也就是说，指令 X 一定会在指令 Y 之前提交
主节点返回执行成功的响应给节点 B，节点 B 再转发给客户端。你看，这样我们就实现了操作的顺序性，保证了指令 X 一定在指令 Y 之前执行
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_初始选举_崩溃恢复_广播消息_images/2a3115e7.png)
#选举
[](https://www.163.com/dy/article/GC588PFS0518E0HL.html)
[](https://www.runoob.com/w3cnote/zookeeper-leader.html)
##角色
leader,follow,boserve
##状态
```asp
LOOKING：选举状态，该状态下的节点认为当前集群中没有领导者，会发起领导者选举。
FOLLOWING ：跟随者状态，意味着当前节点是跟随者。
LEADING ：领导者状态，意味着当前节点是领导者。
OBSERVING： 观察者状态，意味着当前节点是观察者
```
```asp
可以这么理解，每个节点维护一个投票池，每个投票池都包含自己和其他节点推荐的领导者的节点信息，如果有节点赢得大多数投票，那么这时会判断这个节点是否是自己，
如果是自己，那么节点将设置自己的状态为LEADING状态，退出选举；如果不是自己，那么节点将设置自己的状态为FOLLOWING状态，退出选举
```
##过程
![](.z_03_分布式_服务注册中心_02_zookeeper_01_集群模型_选举_images/e1852368.png)
[](https://time.geekbang.org/column/article/232412)
```asp
1.首先，每个节点都会对自己进行投票，然后把投票信息广播给集群中的其他节点

2.节点接收到其他节点的投票信息，然后和自己的投票进行比较，首先zxid较大的优先，如果zxid相同那么则会去选择myid更大者，此时大家都是LOOKING的状态

3.投票完成之后，开始统计投票信息，如果集群中过半的机器都选择了某个节点机器作为leader，那么选举结束

4.最后，更新各个节点的状态，leader改为LEADING状态，follower改为FOLLOWING状态
```
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_事务zxid_选举_事务提交_事务回滚_会话事务_images/5c24a6f5.png)
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_事务zxid_选举_事务提交_事务回滚_会话事务_images/4b5b030f.png)
###投票信息的格式
```asp
<proposedLeader, proposedEpoch, proposedLastZxid，node>
proposedLeader，节点提议的，领导者的集群 ID，也就是在集群配置（比如 myid 配置文件）时指定的 ID。
proposedEpoch，节点提议的，领导者的任期编号。
proposedLastZxid，节点提议的，领导者的事务标识符最大值（也就是最新提案的事务标识符）。
node，投票的节点，比如节点 B
```
###优先级
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_初始选举_崩溃恢复_广播消息_images/0ea63e49.png)
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_初始选举_崩溃恢复_广播消息_images/c4ea34be.png)

```asp
优先检查任期编号（Epoch），任期编号大的节点作为领导者；
如果任期编号相同，比较事务标识符的最大值，值大的节点作为领导者；
如果事务标识符的最大值相同，比较集群 ID，集群 ID 大的节点作为领导者

```
```asp
大多数节点中，事务标识符值最大的节点。另外，ZAB 本质上是通过“见贤思齐，相互推荐”的方式来选举领导者的。也就说，根据领导者 PK，
节点会重新推荐更合适的领导者，最终选举出了大多数节点中数据最完整的节点
```
###更新投票信息
当接收到新的投票信息时，节点会进行领导者 PK，来判断谁更适合当领导者，如果投票信息中提议的节点比自己提议的节点，更适合当领导者，更新投票信息，
推荐投票信息中提议的节点作为领导者，并广播给所有节点


  
#请求处理事务(消息广播)
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_初始选举_崩溃恢复_广播消息_images/4d1bf6aa.png)
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_事务zxid_选举_事务提交_事务回滚_会话事务_images/4aa39367.png)
[](https://www.douban.com/note/208430424/)
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_事务zxid_选举_事务提交_事务回滚_会话事务_images/e49ebda2.png)
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_事务zxid_选举_事务提交_事务回滚_会话事务_images/a1f6f71c.png)
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_事务zxid_选举_事务提交_事务回滚_会话事务_images/58d08fb8.png)
```asp
消息广播
一个事务请求进来之后，Leader节点会将写请求包装成提议（Proposal）事务，并添加一个全局唯一的 64 位递增事务 ID，Zxid。
Leader 节点向集群中其他节点广播Proposal事务，Leader 节点和 Follower 节点是解耦的，通信都会经过一个 FIFO 的消息队列，Leader 会为每一个 Follower 节点分配一个单独的 FIFO 队列，然后把 Proposal 发送到队列中。
Follower 节点收到对应的Proposal之后会把它持久到磁盘上，当完全写入之后，发一个ACK给Leader。
当Leader节点收到超过半数Follower节点的ACK之后会提交本地机器上的事务，同时开始广播commit，Follower节点收到 commit 之后，完成各自的事务提交。
消息广播类似一个分布式事务的两阶段提交模式。在这种模式下，无法处理因Leader在发起事务请求后节点宕机带来的数据不一致问题。因此ZAB协议引入了崩溃恢复机制。
```
[](https://zhuanlan.zhihu.com/p/157317221)
##proposal
写日志
[](https://www.jianshu.com/p/2c5e36b81128)
##commit(proposal阶段，follow已经写入日志了，commit阶段做了什么)
根据日志txn更新内存Database
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_初始选举_崩溃恢复_广播消息_images/b25989b3.png)
```asp
 protected void processCommitted() {
        Request request;
            ...
            request = committedRequests.poll();
            /*
             * We match with nextPending so that we can move to the
             * next request when it is committed. We also want to
             * use nextPending because it has the cnxn member set
             * properly.
             */
            Request pending = nextPending.get();
            if (pending != null &&
                pending.sessionId == request.sessionId &&
                pending.cxid == request.cxid) {
                pending.setHdr(request.getHdr());
                pending.setTxn(request.getTxn());
                pending.zxid = request.zxid;
                currentlyCommitting.set(pending);
                nextPending.set(null);
                sendToNextProcessor(pending);
            } else {
                currentlyCommitting.set(request);
                sendToNextProcessor(request);
            }
        }      
    }
```
##主节点反馈给客户端是否要直到comitted绝大多数才算OK
不一定是主节点，也不需要等到committed绝大多数，具体来说，当节点接收到COMMIT消息后，提交提案，如果是自己接收的写请求，那么这时返回成功响应给客户端
##第一个阶段提议ok之后，第二个committed阶段主节点挂了，那么在选举的时候这写未提交的提议咋处理？
[](http://www.jasongj.com/zookeeper/fastleaderelection/)
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_初始选举_崩溃恢复_广播消息_images/aedb7ac1.png)
因为提案已复制到大多数节点上，领导者选举能保证新的领导者一定包含这个未提交的提案，并最终将它提交
```asp
“如果写请求对应的提案“SET X = 1”未被复制到大多数节点上，比如在领导者广播消息过程中，领导者崩溃了，那么，提案“SET X = 1”，可能被复制到大多数节点上，并提交和之后就不再改变，也可能会被删除。这个行为是未确定的，取决于新的领导者是否包含该提案。”
请教韩老师：
这边set x=1只复制到少数节点上，那么这些少数节点的zxid应该是最大，应该回成为新的leader，也就不会丢数据了啊？
然后这个问题又该如何避免呢？

新领导者不是所有节点中ZXID最大的节点，而是大多数节点中ZXID最大的节点，如果“set x = 1”只复制到少数节点上，ZAB的领导者选举规则，不能保证成为领导者的节点一定是这些“少数节点”。
问题2：对客户端而言，需要支持操作的冥等性，如果写入超时（即在指定时间内，服务器没有成功将指令复制到大多数节点上），重试就可以了。而操作的冥等性，能保证最后的结果是预期的结果（即X的值为1）。


少数节点为何我XID最大我不能成为领导者呢?
不满足“大多数”原则，共识算法本质上是“多数派”算法
```
##写请求
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_初始选举_崩溃恢复_广播消息_images/b346fae5.png)
提案（Proposal）：进行共识协商的基本单元，你可以理解为操作（Operation）或指令（Command），常出现在文档中。事务（Transaction）：
也是指提案，常出现在代码中。比如，pRequest2Txn() 将接收到的请求转换为事务；再比如，未提交提案会持久化存储在事务日志中。
在这里需要你注意的是，这个术语很容易引起误解，因为它不是指更广泛被接受的含义，具有 ACID 特性的操作序列
#服务运行期间的选举(崩溃恢复)
1、选举 zxid 最大的节点作为新的 leader
2、新 leader 将事务日志中尚未提交的消息进行处理
[](https://ask.csdn.net/questions/914703)
[](https://www.cnblogs.com/fanguangdexiaoyuer/p/10311228.html#_label3_0)
[](https://zhuanlan.zhihu.com/p/279955043)
##如果在leader发出了commit之后，各个follow收到commit之前，leader挂掉了，导致follow并没有执行已经提交的提案
解决方案 leader失效后，重新选举出来的leader肯定具备最大的zxid（不考虑这个zxid有没有被提交），只要zxid最大，那么就会被选为leader
(myid也得考虑，这里不是重点），zxid最大说明这个节点肯定包括了所有的最新的提案，当这个节点当选为leader之后，
新的leader会检查自身有没有未被提交的提案，如果有的，则会向集群中发送请求，询问其他follow节点是否存在其提案，
如果超过半数回复ok,则执行提交操作，之后进行数据同步操作，这样就保证了已经被提交的提案不会被丢失。
##奔溃后选leader
zxid最大说明这个节点肯定包括了所有的最新的提案，当这个节点当选为leader
##处理未提交的提案
新的leader会检查自身有没有未被提交的提案，如果有的，则会向集群中发送请求，询问其他follow节点是否存在其提案，
如果超过半数回复ok,则执行提交操作，之后进行数据同步操作
##没有被提交的提案应该被丢弃
假设有这种场景，如果在leader生成提案后，广播之前，leader崩溃了，这个时候的提案是应该被丢弃了，这个ZAB协议时如何解决的呢？

解决方案： Zab 通过巧妙的设计 zxid 来实现这一目的
zxid占据64位，高32位存储epoch编号，这个编号是每选举出一次leader之后都会加一，有种朝代的感觉哈，低32位从0开始，当有新的请求或出现新的提案时，
就会加1，但是重新选择leader之后，就会进行清零； 那么zab时如何借助zxid来解决没有被提交的提案应该丢弃的问题呢？
在旧的leader重启后，因为已经经过一次新的选举了，旧的leader所处的朝代已经落后了，新的leader会要求旧的leader将 它所处的朝代没有被提交 的提案清除，
重新同步最新的提案，这就保证了未被提交的提案进行丢弃；
##lastProcessed
[](https://time.geekbang.org/column/article/232412)
需要你特别注意的是，在跟随者节点正常运行时，dataTree.lastProcessedZxid 表示的是已提交提案的事务标识符最大值，但当跟随者检测到异常，
退出跟随者状态时（在 follower.shutdown() 函数中），ZooKeeper 会将所有未提交提案提交，并使用 lastProcessedZxid 表示节点上提案
（包括刚提交的提案）的事务标识符的最大值，在接下来的领导者选举中，使用的也是该值，也就是说，ZAB 的领导者选举，选举出的是大多数节点中数据最完整的节点。
##处于提交（Committed）状态的提案是可能会改变的(leader未过半宕机又恢复成follow)
```asp
在 ZooKeeper 中，一个提案进入提交（Committed）状态，有两种方式：
被复制到大多数节点上，被领导者提交或接收到来自领导者的提交消息（leader.COMMIT）而被提交。在这种状态下，提交的提案是不会改变的。
另外，在 ZooKeeper 的设计中，在节点退出跟随者状态时（在 follower.shutdown() 函数中），会将所有本地未提交的提案都提交。
需要你注意的是，此时提交的提案，可能并未被复制到大多数节点上，而且这种设计，就会导致 ZooKeeper 中出现，
处于“提交”状态的提案可能会被删除（也就是接收到领导者的 TRUNC 消息而删除的提案）

更准确的说，在 ZooKeeper 中，被复制到大多数节点上的提案，最终会被提交，并不会再改变；而只在少数节点存在的提案，可能会被提交和不再改变，也可能会被删除
```
[](https://time.geekbang.org/column/article/237340)
```
如果写请求对应的提案“SET X = 1”未被复制到大多数节点上，比如在领导者广播消息过程中，领导者崩溃了，那么，提案“SET X = 1”，可能被复制到大多数节点上，
并提交和之后就不再改变，也可能会被删除。这个行为是未确定的，取决于新的领导者是否包含该提案

```
#成员发现和数据同步
成员发现和数据同步不仅让新领导者正式成为领导者，确立了它的领导关系，还解决了各副本的数据冲突，实现了数据副本的一致性
```asp
确立领导关系，也就是在成员发现（DISCOVERY）阶段，领导者和大多数跟随者建立连接，并再次确认各节点对自己当选领导者没有异议，确立自己的领导关系；
处理冲突数据，也就是在数据同步（SYNCHRONIZATION）阶段，领导者以自己的数据为准，解决各节点数据副本的不一致
```
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_初始选举_崩溃恢复_广播消息_images/ecec6566.png)
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_初始选举_崩溃恢复_广播消息_images/667ac49d.png)
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_初始选举_崩溃恢复_广播消息_images/d3811ba6.png)
##四种状态
```asp
ELECTION（选举状态）：表明节点在进行领导者选举；
DISCOVERY（成员发现状态）：表明节点在协商沟通领导者的合法性；
SYNCHRONIZATION（数据同步状态）：表明集群的各节点以领导者的数据为准，修复数据副本的一致性；
BROADCAST（广播状态）：表明集群各节点在正常处理写请求。
```
##只有当集群大多数节点处于广播状态的时候，集群才能提交提案
在 ZooKeeper 中，与领导者“失联”的节点，是不能处理读写请求的，BROADCAST状态才能处理请求
果一个跟随者与领导者的连接发生了读超时，设置了自己的状态为 LOOKING，那么此时它既不能转发写请求给领导者处理，也不能处理读请求，只有当它“找到”领导者后，才能处理读写请求。
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_初始选举_崩溃恢复_广播消息_images/f756110d.png)
当大多数节点进入到广播阶段的时候，领导者才能提交提案，因为提案提交，需要来自大多数节点的确认
##成员发现
主要目的是发现当前大多数节点接收的最新提议
[](https://github.com/969251639/study/wiki/zab)


在当选后，领导者会递增自己的任期编号，并基于任期编号值的大小，来和跟随者协商，最终建立领导关系。具体说的话，就是跟随者会选择任期编号值最大的节点，
作为自己的领导者，而被大多数节点认同的领导者，将成为真正的领导者
先，B、C 会把自己的 ZAB 状态设置为成员发现（DISCOVERY），这就表明，选举（ELECTION）阶段结束了，进入了下一个阶段：
```asp
你要注意，领导者进入到成员发现阶段后，会对任期编号加 1，创建新的任期编号，然后基于新任期编号，创建新的事务标识符（也就是 <2, 0>）
```
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_初始选举_崩溃恢复_广播消息_images/60a7b07a.png)
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_初始选举_崩溃恢复_广播消息_images/e0c1dc27.png)
##数据同步
[](https://time.geekbang.org/column/article/237340)
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_初始选举_崩溃恢复_广播消息_images/fd7885fb.png)
Zookeeper在选举之后，Follower和Observer（统称为Learner）就会去向Leader注册，然后就会开始数据同步的过程
```asp
数据同步包含3个主要值和4种形式。

PeerLastZxid：Learner服务器最后处理的ZXID

minCommittedLog：Leader提议缓存队列中最小ZXID

maxCommittedLog：Leader提议缓存队列中最大ZXID
```
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_事务zxid_选举_事务提交_事务回滚_会话事务_images/b061a8da.png)
###DIFF同步
```asp
如果PeerLastZxid在minCommittedLog和maxCommittedLog之间，那么则说明Learner服务器还没有完全同步最新的数据。

1.首先Leader向Learner发送DIFF指令，代表开始差异化同步，然后把差异数据（从PeerLastZxid到maxCommittedLog之间的数据）提议proposal发送给Learner

2.发送完成之后发送一个NEWLEADER命令给Learner，同时Learner返回ACK表示已经完成了同步

3.接着等待集群中过半的Learner响应了ACK之后，就发送一个UPTODATE命令，Learner返回ACK，同步流程结束
```
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_初始选举_崩溃恢复_广播消息_images/f7dfae27.png)
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_事务zxid_选举_事务提交_事务回滚_会话事务_images/2ec8b3fc.png)
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_初始选举_崩溃恢复_广播消息_images/368cc3ef.png)
B 修复不一致数据，返回 NEWLEADER 消息的确认响应给领导者
###TRUNC+DIFF 同步
```asp
如果Leader刚生成一个proposal，还没有来得及发送出去，此时Leader宕机，重新选举之后作为Follower，但是新的Leader没有这个proposal数据。

举个栗子：

假设现在的Leader是A，minCommittedLog=1，maxCommittedLog=3，刚好生成的一个proposal的ZXID=4，然后挂了。

重新选举出来的Leader是B，B之后又处理了2个提议，然后minCommittedLog=1，maxCommittedLog=5。

这时候A的PeerLastZxid=4，在(1,5)之间。

那么这一条只存在于A的提议怎么处理？

A要进行事务回滚，相当于抛弃这条数据，并且回滚到最接近于PeerLastZxid的事务，对于A来说，也就是PeerLastZxid=3。

流程和DIFF一致，只是会先发送一个TRUNC命令，然后再执行差异化DIFF同步。
```
###TRUNC 同步
```asp
针对PeerLastZxid大于maxCommittedLog的场景，流程和上述一致，事务将会被回滚到maxCommittedLog的记录。

这个其实就更简单了，也就是你可以认为TRUNC+DIFF中的例子，新的Leader B没有处理提议，所以B中minCommittedLog=1，maxCommittedLog=3。

所以A的PeerLastZxid=4就会大于maxCommittedLog了，也就是A只需要回滚就行了，不需要执行差异化同步DIFF了
```
###全量同步 SNAP 同步
```asp
适用于两个场景：

1.PeerLastZxid小于minCommittedLog

2.Leader服务器上没有提议缓存队列，并且PeerLastZxid不等于Leader的最大ZXID

这两种场景下，Leader将会发送SNAP命令，把全量的数据都发送给Learner进行同步
```
###UPTODATE
当领导者接收到来自大多数节点的 NEWLEADER 消息的确认响应，将设置 ZAB 状态为广播。在这里，C 接收到 B 的确认响应，加上 C 自己，就是大多数确认了。
所以，在接收到来自 B 的确认响应后，C 设置自己的 ZAB 状态为广播，并发送 UPTODATE 消息给所有跟随者，通知它们数据同步已经完成了
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_初始选举_崩溃恢复_广播消息_images/23b17f7c.png)
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_初始选举_崩溃恢复_广播消息_images/6087424f.png)
#会话事务
![](.z_03_分布式_服务注册中心_02_zookeeper_01_ZAB共识算法_过半机制_事务zxid_选举_事务提交_事务回滚_会话事务_images/72595ff9.png)
