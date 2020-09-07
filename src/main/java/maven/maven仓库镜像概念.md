
##maven仓库类型
本地仓库地址:${user.home}/.m2/repository
远程仓库地址:${maven.home}/conf/settings.xml和${maven.home}/conf/settings.xml中配置远程仓库地址
中央仓库地址:maven super pom中配置了
依赖库先在本地仓库寻找,找到就不去远程仓库寻找.
```
<repositories>
    <repository>
      <id>central</id>
      <name>Central Repository</name>
      <url>https://repo.maven.apache.org/maven2</url>
      <layout>default</layout>
      <snapshots>
        <enabled>false</enabled>
      </snapshots>
    </repository>
  </repositories>
```
##远程仓库配置方式
1.全局settings.xml:${maven.home}/conf/settings.xml,全局settings.xml中定义了本地仓库路径${user.home}/.m2/repository
2.用户settings.xml:${user.home}/.m2/settings.xml
3.项目pom中配置远端仓库
三者都存在时,他们内容会合并,优先级为:项目pom>用户settings>全局settings
##远程仓库身份验证
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
###仓库镜像Mirrors
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
动态更换镜像仓库,系统变量aliyun有效性为本次执行
mvn clean package -Daliyun='*'

mvn clean package  -DskipTests -X(debug日志) 

测试本地仓库/私有仓库/远程仓库的优先级顺序:
本地仓库 > 私服 （profile）> 远程仓库（repository）和 镜像 （mirror） > 中央仓库 （central）
mvn clean package  -X --settings /Users/chris/Documents/maven/settings.xml | grep -E 'mirror|Repositories' -n10

[lastUpdated order](https://my.oschina.net/polly/blog/2120650)  
[repository order](https://swenfang.github.io/2018/06/03/Maven-Priority/)
