#临界知识
CDN的请求过程,dns->cname->cdn全局负载均衡->cdn就近节点(边缘节点)
CDN原理,CNAME+全局负载均衡
源站:服务的域名
CDN域名:CNAME到的CDN域名
#需求
使用边缘节点 对 静态资源 网络加速
##静态资源(图片,视频)
```asp
对于移动 APP 来说，这些静态资源主要是图片、视频和流媒体信息
对于 Web 网站来说，则包括了 JavaScript 文件、CSS 文件、静态 HTML 文件等等
```
##如何加速?(避免公网骨干网络,就近访问)
```asp
读请求量极大并且对访问速度的要求很高还占据了很高的带宽，这时会出现访问速度慢带宽被占满影响动态请求的问题

如果我们的应用服务器和分布式缓存都部署在北京的机房里，这时一个杭州的用户要访问缓存中的一个视频，那这个视频文件就需要从北京传输到杭州，
期间会经过多个公网骨干网络，延迟很高，会让用户感觉视频打开很慢，严重影响到用户的使用体验
```
#CDN(Content Distribution Network,用户就近物理机)
```asp
静态的资源分发到位于多个地理位置机房中的服务器上，因此它能很好地解决数据就近访问的问题，也就加快了静态资源的访问速度
```
网宿、阿里云、腾讯云
##拓扑
[](https://time.geekbang.org/column/article/154490)
[](https://time.geekbang.org/column/article/120664)
[](https://time.geekbang.org/column/article/10085)
[](https://time.geekbang.org/column/article/3716)
![](.z_09_CDN__images/4fa19777.png)
边缘节点
![](.z_09_CDN__images/1f975350.png)
服务域名
运营商DNS:CNAME,域名映射域名,www.baidu.com->www.a.shifen.com(CDN的域名),解析CDN域名对于的ip(一般是CDN厂商的dns服务器ip)
CDN服务商
CDN域名
CDN转发
##全局负载均衡(ip地理位置)
![](.z_09_CDN__images/669e7c95.png)
##缓存系统(命中率&回源率)
![](.z_09_CDN__images/72c3ab05.png)
##问题
###如何将数据同步到CDN
CDN分发,同步静态资源到所有CDN节点上
配置CDN后，如果通过CDN请求不到静态资源,CDN再请求源站
配置
![](.z_09_CDN__images/e34692f4.png)
###如何将用户的请求映射到 CDN 节点上(DNS CNAME)
[z_08_DNS_.md]
![](.z_09_CDN__images/1af4359c.png)
###如何根据用户的地理位置信息选择到比较近的节点(全局负载均衡器,Global Server Load Balance)
```asp
1.让流量平均分配使得下面管理的服务器的负载更平均
2.流量流经的服务器与流量源头在地缘上是比较接近
```
###对于无法缓存的动态资源，你觉得 CDN 也能有加速效果吗？
cache-control允许缓存的动态资源可以被CDN缓存。不允许缓存的动态资源会回源
cdn一般有专用的高速网络直连源站，或者是动态路径优化，所以动态资源回源要比通过公网速度快很多
###CDN缓存更新
cdn厂家会提供一些删除和更新的接口
缓存刷新
![](.z_09_CDN__images/42ee349d.png)
####ip地理位置划分
```asp
1.GSLB 可以通过多种策略来保证返回的 CDN 节点和用户尽量保证在同一地缘区域，比如说可以将用户的 IP 地址按照地理位置划分为若干个区域，然后将 CDN 节点对应到一个区域上，根据用户所在区域来返回合适的节点；
2.也可以通过发送数据包测量 RTT 的方式来决定返回哪一个节点。
```
##应用
![](.z_09_CDN__images/05d5e880.png)


厂商有金山,腾讯,阿里
###阿里云CDN服务
[](https://www.aliyun.com/product/cdn)
阿里云 SSD固态硬盘
自定义指定资源内容的缓存过期时间规则
