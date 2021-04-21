##linux hostname相关配置文件
/proc/sys/kernel/hostname  
/etc/sysconfig/network  

   
 
/etc/nsswitch.conf  
/etc/host.conf  

/etc/resolv.conf  

hostname从/proc/sys/kernel/hostname获取参数,系统启动时/proc/sys/kernel/hostname从
/etc/sysconfig/network获取hostname,/etc/sysconfig/network没有获取到hostname则默认为localhost。
如果获取的是localhost,则会通过ip反向查找/etc/hosts中相同ip的hostname 

另外可以通过hostname命令动态更改/proc/sys/kernel/hostname,此方案是临时方案,重启linux会重新走上面过程
[hostname配置](https://www.cnblogs.com/kerrycode/p/3595724.html)
[三个文件的关系](https://jaminzhang.github.io/linux/deep-understanding-of-linux-hostname/)

###/etc/hosts
[配置ip,hostname,域名的映射关系](https://blog.csdn.net/aeolus_pu/article/details/9037377)

##局域网内主机名==ip原理
gethostbyname

gethostbyaddr

##局域网DNS
搭建内网DNS服务来完成主机名和IP的映射；

##DNS解析原理与源码
[dns解析源码分析](https://zhuanlan.zhihu.com/p/32531969)
1.获取DNS服务器：
当一台设备连到路由器之后，路由器通过DHCP给它分配一个IP地址，并告诉它DNS服务器
当我的电脑连上wifi的时候，会发一个DHCP Request的广播，路由器收到这个广播后就会向我的电脑分配一个IP地址并告知DNS服务器。
dns服务器记录在/etc/resolv.conf
```
$ cat /etc/resolv.conf                  
search mioffice.cn
nameserver 10.237.8.8
nameserver 10.237.8.9

```
然后用这个nameservers列表去初始化一个socket pool即套接字池，套接字是用来发请求的。在需要做域名解析的时候会从套接字池里面取出一个socket，
并传递想要用的server_index，初始化的时候是0，即取第一个DNS服务IP地址，一旦解析请求两次都失败了，则server_index + 1使用下一个DNS服务。

```
unsigned server_index =
        (first_server_index_ + attempt_number) % config.nameservers.size();
    // Skip over known failed servers.
    // 最大attempts数为2，在构造DnsConfig设定的
    server_index = session_->NextGoodServerIndex(server_index);

```
![](/Users/chris/workspace/xsource/linux/src/main/java/host/images/host\&dns解析.jpg)
##域名和主机名关系
[](https://zhuanlan.zhihu.com/p/43231547)
主机名的含义是机器本身的名字，域名是方面记录IP地址才做的一种IP映射；通过上述介绍可以看到，二者有共性：都能对应到一个唯一的IP上，
从应用场景上可以这么简单理解二者的区别：主机名用于局域网中；域名用于公网中

##hostname uname host nslookup命令
hostname:
获取ip:hostname -i
获取hostname:hostname

##dns命令
查看dns服务器:cat /etc/resolv.conf


##java InetAddress.getLocalHost分析
[](https://blog.csdn.net/raintungli/article/details/8191701)
```
JNIEXPORT jstring JNICALL
Java_java_net_Inet6AddressImpl_getLocalHostName(JNIEnv *env, jobject this) {
    char hostname[NI_MAXHOST+1];
 
    hostname[0] = '\0';
    if (JVM_GetHostName(hostname, MAXHOSTNAMELEN)) {
	/* Something went wrong, maybe networking is not setup? */
	strcpy(hostname, "localhost");
    } else {
       .....
   }
}
```
```
JVM_LEAF(int, JVM_GetHostName(char* name, int namelen))
  JVMWrapper("JVM_GetHostName");
  return hpi::get_host_name(name, namelen);
JVM_END

```
```
inline int hpi::get_host_name(char* name, int namelen){
  return ::gethostname(name, namelen);
}

```
获取linux内核hostname变量

##常见概念 A,AAAA,CNAME
A就是把域名解析到一个IPv4地址，
而AAAA是解析到IPv6地址，
CNAME是解析到另外一个域名
