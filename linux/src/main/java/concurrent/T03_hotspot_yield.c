public static native void yield();


// 从当前CPU的就绪队列中取出一个任务执行，并将当前任务放回队列中去
static void do_sched_yield(void)
{
  // ...
  current->sched_class->yield_task(rq);
  // ...
  schedule();
}

static void yield_task_fair(struct rq *rq)
{
	// ...
	clear_buddies(cfs_rq, se);
	if (curr->policy != SCHED_BATCH) {
		update_rq_clock(rq);
		update_curr(cfs_rq); // 更新当前任务的vruntime等信息
		rq_clock_skip_update(rq);
	}
	set_skip_buddy(se); // 设置当前任务为cfs_rq->skip
}


