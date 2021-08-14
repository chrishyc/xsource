"""
int pthread_create(pthread_t *tid, const pthread_attr_t *attr, void *(*func) (void *), void *arg);

pthread_create用于创建一个线程，成功返回0，否则返回Exxx（为正数）。

pthread_t *tid：线程id的类型为pthread_t，通常为无符号整型，当调用pthread_create成功时，通过*tid指针返回。
const pthread_attr_t *attr：指定创建线程的属性，如线程优先级、初始栈大小、是否为守护进程等。可以使用NULL来使用默认值，通常情况下我们都是使用默认值。
void *(*func) (void *)：函数指针func，指定当新的线程创建之后，将执行的函数。
void *arg：线程将执行的函数的参数。如果想传递多个参数，请将它们封装在一个结构体中
"""




"""
互斥锁的相关操作函数如下：

#include <pthread.h>

int pthread_mutex_lock(pthread_mutex_t * mptr);
int pthread_mutex_unlock(pthread_mutex_t * mptr);

在对临界资源进行操作之前需要pthread_mutex_lock先加锁，操作完之后pthread_mutex_unlock再解锁。
而且在这之前需要声明一个pthread_mutex_t类型的变量，用作前面两个函数的参数
"""




"""
线程之间的同步
#include <pthread.h>

int pthread_cond_wait(pthread_cond_t *cptr, pthread_mutex_t *mptr);
int pthread_cond_signal(pthread_cond_t *cptr);
int pthread_cond_broadcast (pthread_cond_t * cptr);
int pthread_cond_timedwait (pthread_cond_t * cptr, pthread_mutex_t *mptr, const struct timespec *abstime);


"""

/*
 是否熟悉POSIX多线程编程技术？如熟悉，编写程序完成如下功能：
  1）有一int型全局变量g_Flag初始值为0；
  2）在主线称中起动线程1，打印“this is thread1”，并将g_Flag设置为1
  3）在主线称中启动线程2，打印“this is thread2”，并将g_Flag设置为2
  4）线程序1需要在线程2退出后才能退出
  5）主线程在检测到g_Flag从1变为2，或者从2变为1的时候退出
   */

 #include<stdio.h>
 #include<stdlib.h>
 #include<pthread.h>
 #include<errno.h>
 #include<unistd.h>

 typedef void* (*fun)(void*);

 int g_Flag=0;
 static pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
 static pthread_cond_t cond = PTHREAD_COND_INITIALIZER;

 void* thread1(void*);
 void* thread2(void*);

 /*
  *  when program is started, a single thread is created, called the initial thread or main thread.
  *  Additional threads are created by pthread_create.
  *  So we just need to create two thread in main().
  */

 int main(int argc, char** argv)
 {
 	printf("enter main\n");
 	pthread_t tid1, tid2;
 	int rc1=0, rc2=0;
 	rc2 = pthread_create(&tid2, NULL, thread2, NULL);
 	if(rc2 != 0)
 		printf("%s: %d\n",__func__, strerror(rc2));

 	rc1 = pthread_create(&tid1, NULL, thread1, &tid2);
 	if(rc1 != 0)
 		printf("%s: %d\n",__func__, strerror(rc1));

 	pthread_cond_wait(&cond, &mutex);
 	printf("leave main\n");
 	exit(0);
 }

 /*
  * thread1() will be execute by thread1, after pthread_create()
  * it will set g_Flag = 1;
  */
 void* thread1(void* arg)
 {
 	printf("enter thread1\n");
 	printf("this is thread1, g_Flag: %d, thread id is %u\n",g_Flag, (unsigned int)pthread_self());
 	pthread_mutex_lock(&mutex);
 	if(g_Flag == 2)
 		pthread_cond_signal(&cond);
 	g_Flag = 1;
 	printf("this is thread1, g_Flag: %d, thread id is %u\n",g_Flag, (unsigned int)pthread_self());
 	pthread_mutex_unlock(&mutex);
 	pthread_join(*(pthread_t*)arg, NULL);
 	printf("leave thread1\n");
 	pthread_exit(0);
 }

 /*
  * thread2() will be execute by thread2, after pthread_create()
  * it will set g_Flag = 2;
  */
 void* thread2(void* arg)
 {
 	printf("enter thread2\n");
 	printf("this is thread2, g_Flag: %d, thread id is %u\n",g_Flag, (unsigned int)pthread_self());
 	pthread_mutex_lock(&mutex);
 	if(g_Flag == 1)
 		pthread_cond_signal(&cond);
 	g_Flag = 2;
 	printf("this is thread2, g_Flag: %d, thread id is %u\n",g_Flag, (unsigned int)pthread_self());
 	pthread_mutex_unlock(&mutex);
 	printf("leave thread2\n");
 	pthread_exit(0);
 }
