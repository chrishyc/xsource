#后台运行
nohup ./mqnamesrv >./log.txt  2>&1 &
```asp
nohup:忽略内部的挂断信号，不挂断运行
&:后台运行
>:重定向
2>&1:
0表示标准输入
1表示标准输出
2表示标准错误输出
> 默认为标准输出重定向，与 1> 相同
2>&1 意思是把 标准错误输出 重定向到 标准输出.
&>file 意思是把 标准输出 和 标准错误输出 都重定向到文件file中
```
##man使用
```asp
1      User Commands

2      System Calls

3      C Library Functions

4      Devices and Special Files

5      File Formats and Conventions

6      Games et. Al.

7      Miscellanea

8      System Administration tools and Deamons
```
