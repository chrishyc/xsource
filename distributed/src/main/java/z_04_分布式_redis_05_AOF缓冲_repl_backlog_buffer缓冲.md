##AOF缓冲
AOF重放缓存只给AOF用
![](.z_04_分布式_redis_05_AOF缓冲_repl_backlog_buffer缓冲_images/a52450e0.png)
##repl_backlog_buffer
环形数组,为了可以让从库继续增量同步
![](.z_04_分布式_redis_05_AOF缓冲_repl_backlog_buffer缓冲_images/6e430efe.png)
