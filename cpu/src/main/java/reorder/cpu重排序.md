核心问题:缓存优化，指令流水线优化和共享数据并发的问题
##1.cpu内存屏障
##2.硬件内存屏障
lock指令
##3.软件(指令)内存屏障
?fence
##4.hotspot内存屏障规范
volatile内存屏障实现方案,使用硬件lock指令
##hanppens-before重排序
##as-if-serial并行执行
SingleThreadPool串行执行
线程池拒绝执行策略
##UMA vs NUMA
ZGC
