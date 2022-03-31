##threadlocal 模型
![](/Users/chris/workspace/xsource/linux/src/main/java/concurrent/images/threadlocal对象模型.jpg)
[](https://www.cnblogs.com/Ccwwlx/p/13581004.html)
![](.z_threadlocal_问题清单_images/f4991752.png)
##threadlocal内存泄露问题?
##threadlocal为啥不把value搞成弱引用?
[](https://cloud.tencent.com/developer/article/1769423#:~:text=%E3%80%8C%E4%B8%8D%E8%AE%BE%E7%BD%AE%E4%B8%BA%E5%BC%B1%E5%BC%95%E7%94%A8,%E5%85%B6%E8%AE%BE%E7%BD%AE%E4%B8%BA%E5%BC%BA%E5%BC%95%E7%94%A8%E3%80%82%E3%80%8D)
不设置为弱引用，是因为不清楚这个Value除了map的引用还是否还存在其他引用，如果不存在其他引用，当GC的时候就会直接将这个Value干掉了，
而此时我们的ThreadLocal还处于使用期间，就会造成Value为null的错误，所以将其设置为强引用
##threadlocal为啥static
static 防止无意义多实例
[](https://www.zhihu.com/question/35250439)
