#内存
![](.z_05_flink_06_内存优化_images/84017850.png)
### **Flink内存优化**

​	在大数据领域，大多数开源框架（Hadoop、Spark、Flink）都是基于JVM运行，但是JVM的内存管理机制往往存在着诸多类似OutOfMemoryError的问题，主要是因为创建过多的对象实例而超过JVM的最大堆内存限制，却没有被有效回收掉，这在很大程度上影响了系统的稳定性，尤其对于大数据应用，面对大量的数据对象产生，仅仅靠JVM所提供的各种垃圾回收机制很难解决内存溢出的问题。在开源框架中有很多框架都实现了自己的内存管理，例如Apache Spark的Tungsten项目，在一定程度上减轻了框架对JVM垃圾回收机制的依赖，从而更好地使用JVM来处理大规模数据集。

Flink也基于JVM实现了自己的内存管理，将JVM根据内存区分为Unmanned Heap、Flink Managed Heap、Network Buffers三个区域。在Flink内部对Flink Managed Heap进行管理，在启动集群的过程中直接将堆内存初始化成Memory Pages Pool，也就是将内存全部以二进制数组的方式占用，形成虚拟内存使用空间。新创建的对象都是以序列化成二进制数据的方式存储在内存页面池中，当完成计算后数据对象Flink就会将Page置空，而不是通过JVM进行垃圾回收，保证数据对象的创建永远不会超过JVM堆内存大小，也有效地避免了因为频繁GC导致的系统稳定性问题。

![1592106823298](C:\Users\root\AppData\Roaming\Typora\typora-user-images\1592106823298.png)



#### 1) **JobManager配置**

​	JobManager在Flink系统中主要承担管理集群资源、接收任务、调度Task、收集任务状态以及管理TaskManager的功能，JobManager本身并不直接参与数据的计算过程中，因此JobManager的内存配置项不是特别多，只要指定JobManager堆内存大小即可。

jobmanager.heap.size：设定JobManager堆内存大小，默认为1024MB。

#### 2) **TaskManager配置**

​	TaskManager作为Flink集群中的工作节点，所有任务的计算逻辑均执行在TaskManager之上，因此对TaskManager内存配置显得尤为重要，可以通过以下参数配置对TaskManager进行优化和调整。

- taskmanager.heap.size：设定TaskManager堆内存大小，默认值为1024M，如果在Yarn的集群中，TaskManager取决于Yarn分配给TaskManager Container的内存大小，且Yarn环境下一般会减掉一部分内存用于Container的容错。

- taskmanager.jvm-exit-on-oom：设定TaskManager是否会因为JVM发生内存溢出而停止，默认为false，当TaskManager发生内存溢出时，也不会导致TaskManager停止。

- taskmanager.memory.size：设定TaskManager内存大小，默认为0，如果不设定该值将会使用taskmanager.memory.fraction作为内存分配依据。

- taskmanager.memory.fraction：设定TaskManager堆中去除Network Buffers内存后的内存分配比例。该内存主要用于TaskManager任务排序、缓存中间结果等操作。例如，如果设定为0.8，则代表TaskManager保留80%内存用于中间结果数据的缓存，剩下20%的内存用于创建用户定义函数中的数据对象存储。注意，该参数只有在taskmanager.memory.size不设定的情况下才生效。

- taskmanager.memory.off-heap：设置是否开启堆外内存供Managed Memory或者Network Buffers使用。

- taskmanager.memory.preallocate：设置是否在启动TaskManager过程中直接分配TaskManager管理内存。

- taskmanager.numberOfTaskSlots：每个TaskManager分配的slot数量。

### 3. **Flink的网络缓存优化**

​	Flink将JVM堆内存切分为三个部分，其中一部分为Network Buffers内存。Network Buffers内存是Flink数据交互层的关键内存资源，主要目的是缓存分布式数据处理过程中的输入数据。。通常情况下，比较大的Network Buffers意味着更高的吞吐量。如果系统出现“Insufficient number of network buffers”的错误，一般是因为Network Buffers配置过低导致，因此，在这种情况下需要适当调整TaskManager上Network Buffers的内存大小，以使得系统能够达到相对较高的吞吐量。

​	目前Flink能够调整Network Buffer内存大小的方式有两种：一种是通过直接指定Network Buffers内存数量的方式，另外一种是通过配置内存比例的方式。

#### 1) **设定Network Buffer内存数量****（过时了）**

直接设定Nework Buffer数量需要通过如下公式计算得出：

**NetworkBuffersNum = total-degree-of-parallelism \* intra-node-parallelism * n**

其中total-degree-of-parallelism表示每个TaskManager的总并发数量，intra-node-parallelism表示每个TaskManager输入数据源的并发数量，n表示在预估计算过程中Repar-titioning或Broadcasting操作并行的数量。intra-node-parallelism通常情况下与Task-Manager的所占有的CPU数一致，且Repartitioning和Broadcating一般下不会超过4个并发。可以将计算公式转化如下：

**NetworkBuffersNum  = <slots-per-TM>^2 \* < TMs>* 4**

​	其中slots-per-TM是每个TaskManager上分配的slots数量，TMs是TaskManager的总数量。对于一个含有20个TaskManager，每个TaskManager含有8个Slot的集群来说，总共需要的Network Buffer数量为8^2**20*4=5120个，因此集群中配置Network Buffer内存的大小约为160M较为合适。

​	计算完Network Buffer数量后，可以通过添加如下两个参数对Network Buffer内存进行配置。其中segment-size为每个Network Buffer的内存大小，默认为32KB，一般不需要修改，通过设定numberOfBuffers参数以达到计算出的内存大小要求。

- taskmanager.network.numberOfBuffers：指定Network堆栈Buffer内存块的数量。

- taskmanager.memory.segment-size.：内存管理器和Network栈使用的内存Buffer大小，默认为32KB。

#### 2) **设定Network内存比例****（推荐）**

从1.3版本开始，Flink就提供了通过指定内存比例的方式设置Network Buffer内存大小。

 

- taskmanager.network.memory.fraction: JVM中用于Network Buffers的内存比例。

- taskmanager.network.memory.min: 最小的Network Buffers内存大小，默认为64MB。

- taskmanager.network.memory.max: 最大的Network Buffers内存大小，默认1GB。

- taskmanager.memory.segment-size: 内存管理器和Network栈使用的Buffer大小，默认为32KB。
[](https://developer.aliyun.com/article/57815)
[](https://www.modb.pro/db/124111)
