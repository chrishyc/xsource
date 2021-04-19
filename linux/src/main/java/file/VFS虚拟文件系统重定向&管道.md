##常用概念
mount
##docker原理
##lsof查看进程打开的文件列表
echo $$
lsof -p $$: 查看bash打开的文件
##文件挂载
[](https://www.cnblogs.com/cangqinglang/p/12170828.html#:~:text=%E6%8C%87%E7%9A%84%E5%B0%B1%E6%98%AF%E5%B0%86%E8%AE%BE%E5%A4%87,%E6%A0%91%E5%BD%A2%E7%9B%AE%E5%BD%95%E7%BB%93%E6%9E%84%E4%B8%AD%E3%80%82)
###目录挂载目录

##镜像文件
[](https://baike.baidu.com/item/%E9%95%9C%E5%83%8F/1574)
##重定向,标准输入 输出
重定向操作符>,<: 文件描述符n位于左边,如果需要位于右侧需&n


输出重定向,输入重定向
0>&
0<&

ls 1 < hello.txt 1>heheda.txt
```
command > file	将输出重定向到 file。
command < file	将输入重定向到 file。
command >> file	将输出以追加的方式重定向到 file。
n > file	将文件描述符为 n 的文件重定向到 file。
n >> file	将文件描述符为 n 的文件以追加的方式重定向到 file。
n >& m	将输出文件 m 和 n 合并。
n <& m	将输入文件 m 和 n 合并。
<< tag	将开始标记 tag 和结束标记 tag 之间的内容作为输入。
```
##管道

[管道原理](https://segmentfault.com/a/1190000009528245#:~:text=%E5%9C%A8Linux%E4%B8%AD%EF%BC%8C%E7%AE%A1%E9%81%93%E7%9A%84,%E7%89%A9%E7%90%86%E9%A1%B5%E9%9D%A2%E8%80%8C%E5%AE%9E%E7%8E%B0%E7%9A%84%E3%80%82&text=%E8%BF%99%E6%A0%B7%EF%BC%8C%E7%94%A8%E6%88%B7%E7%A8%8B%E5%BA%8F%E7%9A%84%E7%B3%BB%E7%BB%9F,%E7%AE%A1%E9%81%93%E8%BF%99%E4%B8%80%E7%89%B9%E6%AE%8A%E6%93%8D%E4%BD%9C%E3%80%82)
[管道原理书籍](understanding linux kernel:pipes)
head -8 test.txt | tail -l
![管道创建两个子进程](/Users/chris/workspace/xsource/linux/src/main/java/file/images/管道_3.jpg)
{a=9;echo "fafa";} | cat

```
root@b7a2ed9bcc63:/# echo $$
11
root@b7a2ed9bcc63:/# { echo $BASHPID; read x; } | { cat ; echo $BASHPID; read y; }
41

```
```
root@b7a2ed9bcc63:/# ps -fe | grep 11
root        11     0  0 14:27 pts/1    00:00:00 /bin/bash
root        41    11  0 14:44 pts/1    00:00:00 /bin/bash
root        42    11  0 14:44 pts/1    00:00:00 /bin/bash
root        45    23  0 14:44 pts/2    00:00:00 grep --color=auto 11
```
```
root@b7a2ed9bcc63:/# cd /proc/11/fd
root@b7a2ed9bcc63:/proc/11/fd# ls -l
total 0
lrwx------ 1 root root 64 Apr 19 14:27 0 -> /dev/pts/1
lrwx------ 1 root root 64 Apr 19 14:27 1 -> /dev/pts/1
lrwx------ 1 root root 64 Apr 19 14:27 2 -> /dev/pts/1
lrwx------ 1 root root 64 Apr 19 14:46 255 -> /dev/pts/1
root@b7a2ed9bcc63:/proc/11/fd# cd /proc/41/fd
root@b7a2ed9bcc63:/proc/41/fd# ls -l
total 0
lrwx------ 1 root root 64 Apr 19 14:46 0 -> /dev/pts/1
l-wx------ 1 root root 64 Apr 19 14:46 1 -> 'pipe:[21001]'
lrwx------ 1 root root 64 Apr 19 14:46 2 -> /dev/pts/1
lrwx------ 1 root root 64 Apr 19 14:46 255 -> /dev/pts/1
root@b7a2ed9bcc63:/proc/41/fd# cd /proc/42/fd
root@b7a2ed9bcc63:/proc/42/fd# ls -l
total 0
lr-x------ 1 root root 64 Apr 19 14:46 0 -> 'pipe:[21001]'
lrwx------ 1 root root 64 Apr 19 14:46 1 -> /dev/pts/1
lrwx------ 1 root root 64 Apr 19 14:46 2 -> /dev/pts/1
lrwx------ 1 root root 64 Apr 19 14:46 255 -> /dev/pts/1

```
```
root@b7a2ed9bcc63:/proc/42/fd# lsof -op 41
COMMAND PID USER   FD   TYPE DEVICE OFFSET    NODE NAME
bash     41 root  cwd    DIR  0,154        2364729 /
bash     41 root  rtd    DIR  0,154        2364729 /
bash     41 root  txt    REG  0,154        2362625 /usr/bin/bash
bash     41 root  mem    REG  254,1        2362625 /usr/bin/bash (path dev=0,154)
bash     41 root  mem    REG  254,1        2363546 /usr/lib/x86_64-linux-gnu/libnss_files-2.31.so (path dev=0,154)
bash     41 root  mem    REG  254,1        2363481 /usr/lib/x86_64-linux-gnu/libc-2.31.so (path dev=0,154)
bash     41 root  mem    REG  254,1        2363492 /usr/lib/x86_64-linux-gnu/libdl-2.31.so (path dev=0,154)
bash     41 root  mem    REG  254,1        2363601 /usr/lib/x86_64-linux-gnu/libtinfo.so.6.2 (path dev=0,154)
bash     41 root  mem    REG  254,1        2363459 /usr/lib/x86_64-linux-gnu/ld-2.31.so (path dev=0,154)
bash     41 root    0u   CHR  136,1    0t0       4 /dev/pts/1
bash     41 root    1w  FIFO   0,11    0t0   21001 pipe
bash     41 root    2u   CHR  136,1    0t0       4 /dev/pts/1
bash     41 root  255u   CHR  136,1    0t0       4 /dev/pts/1

```

## shell解释执行
$$
$BASHPID
##read函数
[read函数](https://www.runoob.com/linux/linux-comm-read.html)
```
read -p "输入网站名:" website
echo "你输入的网站名是 $website" 
```
##linux常用目录
[](https://www.cnblogs.com/sdu20112013/p/11313585.html)
/dev/
```
/dev/null：无限数据接收设备,相当于黑洞
/dev/zero：无限零资源
/dev/tcp
```
##进程文件
cat /proc/pid/fd
/proc/$$/
查看进程树:pstree
查看进程文件:lsof -p pid

lsof -op $$查看进程文件
###父子bash进程
进程内变量:x=100
环境变量:export x=100

##socket文件
/dev/tcp
##swap分区
##文件读写加锁
##查看页缓存
echo $$
pcstat -pid $$: 查看页缓存
