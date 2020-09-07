
##1.需求:
定义本地仓库地址,远程仓库地址和远程仓库身份验证信息
##2.方案:
全局settings.xml:${maven.home}/conf/settings.xml,全局settings.xml中定义了本地仓库路径${user.home}/.m2/repository
用户settings.xml:${user.home}/.m2/settings.xml
两者都存在时,他们内容会合并,且用户settings.xml优先级高
##3.实现:
###1.Servers
```
<servers>
    <server>
      <id>central</id>
      <username>my_login</username>
      <password>my_password</password>
    </server>
  </servers>
```
id:将要连接的仓库/镜像server的id
username,password:连接server的用户和密码

###2.repositories

```
<repositories>
    <repository>
      <id>codehausSnapshots</id>
      <name>Codehaus Snapshots</name>
      <releases>
        <enabled>false</enabled>
        <updatePolicy>always</updatePolicy>
        <checksumPolicy>warn</checksumPolicy>
      </releases>
      <snapshots>
        <enabled>true</enabled>
        <updatePolicy>never</updatePolicy>
        <checksumPolicy>fail</checksumPolicy>
      </snapshots>
      <url>http://snapshots.maven.codehaus.org/maven2</url>
    </repository>
</repositories>

releases, snapshots:构建类型

```

###3.Mirrors
mirror镜像可以拦截对远程仓库的请求,改变对目标仓库的下载地址  
```
<mirrors>
    <mirror>
      <id>planetmirror.com</id>
      <name>PlanetMirror Australia</name>
      <url>http://downloads.planetmirror.com/pub/maven2</url>
      <mirrorOf>central</mirrorOf>
    </mirror>
  </mirrors>
```
id:mirror唯一标识符
url:连接远程仓库的url
mirrorOf:给id为central的远程仓库做镜像,如果填*,就会替代所有仓库
高级镜像配置:
* = everything
external:* = everything not on the localhost and not file based.
repo,repo1 = repo or repo1
\*,!repo1 = everything except repo1

###4.案例
动态更换镜像仓库:
mvn help:effective-settings -Daliyun='*'

