#crontab原理
crond守护进程是在系统启动时由init进程启动的，受init进程的监视，如果它不存在了，会被init进程重新启动。这个守护进程每分钟唤醒一次，并通过检查crontab文件判断需要做什么
![](.T_01_crontab_images/2718f621.png)
一个守护线程轮询
![](.T_01_crontab_images/22c4111b.png)
Cron  examines  all  stored crontabs and checks each job to see if it needs to be run in the current minute
![](.T_01_crontab_images/3eb4abd9.png)
执行时fork子进程进行执行,不会等到子进程的结束
 it forks and execs that job, so a running job will not block the creation of a new job.
 [](https://stackoverflow.com/questions/9629447/will-cron-job-start-a-new-thread-or-wait-for-existing-one-to-complete)
 ![](.T_01_crontab_images/7edce0d6.png)
#crontab文件
##/etc/crontab
##/etc/cron.d/
directory that contains system cronjobs stored for different users.
##/var/spool/cron
Cron searches /var/spool/cron for crontab files which are named after accounts in /etThe found crontabs are loaded into the memoryc/passwd
![](.T_01_crontab_images/88cadba4.png)
#任务耗时阻塞
fork子进程执行
[](https://cloud.tencent.com/developer/article/1183262)
#源码
[](https://github.com/mirror/busybox/blob/HEAD/miscutils/crond.c#)
任务加载到内存后，直接遍历
```asp
typedef struct CronFile {
	struct CronFile *cf_next;
	struct CronLine *cf_lines;
	char *cf_username;
	smallint cf_wants_starting;     /* bool: one or more jobs ready */
	smallint cf_has_running;        /* bool: one or more jobs running */
	smallint cf_deleted;            /* marked for deletion (but still has running jobs) */
} CronFile;

typedef struct CronLine {
	struct CronLine *cl_next;
	char *cl_cmd;                   /* shell command */
	pid_t cl_pid;                   /* >0:running, <0:needs to be started in this minute, 0:dormant */
#define START_ME_REBOOT -2
#define START_ME_NORMAL -1
#if ENABLE_FEATURE_CROND_CALL_SENDMAIL
	int cl_empty_mail_size;         /* size of mail header only, 0 if no mailfile */
	char *cl_mailto;                /* whom to mail results, may be NULL */
#endif
	char *cl_shell;
	/* ordered by size, not in natural order. makes code smaller: */
	char cl_Dow[7];                 /* 0-6, beginning sunday */
	char cl_Mons[12];               /* 0-11 */
	char cl_Hrs[24];                /* 0-23 */
	char cl_Days[32];               /* 1-31 */
	char cl_Mins[60];               /* 0-59 */
} CronLine;
```
![](.T_01_crontab_images/69a274f4.png)
