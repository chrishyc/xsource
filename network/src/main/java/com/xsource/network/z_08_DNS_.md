#DNS
##拓扑
![](.z_08_DNS__images/afb9d689.png)
![](.z_08_DNS__images/0dcbc06d.png)
```asp
1.hosts(本机)->  
2.Local DNS(运营商)->  
3.根 DNS 返回顶级 DNS（.com）的地址->  
4.com 顶级 DNS 得到 baidu.com 的域名服务器地址
5.再从 baidu.com 的域名服务器中查询到 www.baidu.com 对应的 IP 地址
6.写入 Local DNS 的解析结果缓存,下一次的解析同一个域名就不需要做 DNS 的迭代查询
```
##权威机构
阿里代理售卖域名ip
##A记录(域名<->ip)
域名对应的 IP 地址
##CNAME(域名<->域名)
当前域名的解析要跳转到另一个域名的解析上
##ip vs 域名
域名可以在阿里云购买
固定ip可以在运营商购买
##DNS负载均衡
对于域名服务器:域名可以有多个IP（或多条CNAME记录），这是提供负载均衡的一种方式
对于应用服务器:每台服务器的IP是唯一的,每台服务器可以绑定很多域名，每个域名都是可以解析到这台服务器的IP，这样就可以实现很多域名访问同一台服务器
##应用
[aliDNS](https://www.alidns.com/)

