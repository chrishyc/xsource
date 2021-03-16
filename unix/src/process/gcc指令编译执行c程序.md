## 1.配置gcc
which gcc查看gcc版本
使用/usr/bin/gcc,如果默认是/usr/local/bin,可以更改环境文件~/.bash_profile为export PATH=/usr/bin:$PATH
## 2.查看gcc /usr/include
usr:unix system resource
gcc -v
/Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX.sdk/usr/include/c++/4.2.1

## 3.编译gcc
进入目录make all
## 4.导入头文件
将unix环境编程的头文件复制到/usr/include
sudo cp include/apue.h /usr/include/
sudo cp lib/error.c  /usr/include/
## 5.编译执行.c程序
gcc PrintPid.c
./PrintPid.o

