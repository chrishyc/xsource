##临界知识
一次系统调用批量发送fd状态
类型拆分,只批量发送活跃的fd
线程资源很大,1M
线程太多,cpu需要大量上下文切换,导致work cpu时间减少很多
##accept阻塞
![](.z_linux_01_NIO_accept_select_poll_epoll_images/8b66d7e3.png)
##accept非阻塞 
![](.z_linux_01_NIO_accept_select_poll_epoll_images/0fa9da4c.png)
##select
![](.z_linux_01_NIO_accept_select_poll_epoll_images/8fd9c927.png)
##poll
##epoll
![](.z_linux_01_NIO_accept_select_poll_epoll_images/44b84af3.png)


