##内核内存管理对象
内核
页
内存管理单元
物理内存
虚拟内存
##页数据结构,描述物理内存
struct page{
    __count
}
##区

write&read
mmap
sendf

##
文件内存映射：文件地址空间mmap，进程虚拟地址空间mmap
物理内存分页管理,4GB,每页4KB
进程虚拟内存分页管理

物理页
```
struct page  {
    unsigned long    flags;
    atomic_t    _count;
    atomic_t    _mapcount;// 页表被映射的次数，也就是说page同时被多少个进程所共享
    unsigned long    private;
    /**
    _mapping有三种含义：
    
            a.如果mapping  =  0，说明该page属于交换缓存（swap cache); 当需要地址空间时会指定交换分区的地址空间swapper_space;
    
            b.如果mapping !=  0,  bit[0]  =  0,  说明该page属于页缓存或者文件映射，mapping指向文件的地址空间address_space；
    
            c.如果mapping !=  0,  bit[0]  !=0 说明该page为匿名映射，mapping指向struct  anon_vma对象；
    **/
    struct address_space    *mapping;
    pgoff_t    index;
    struct list_head    lru;
    void*    virtual;//虚拟内存地址
};
```


页缓存就是将一个文件在内存中的所有物理页所组成的一种树形结构，我们称之为基数树，用于管理属于同一个文件在内存中的缓存内容

address_space结构体是将页缓存和文件系统关联起来的桥梁,物理页缓存映射

文件地址空间address_space
![inode](/Users/chris/workspace/xsource/linux/src/main/java/file/images/vfs_inode数据结构.jpeg)
```
struct address_space {
    struct inode*    host;/*指向与该address_space相关联的inode节点*/
    struct radix_tree_root    page_tree;/*所有页形成的基数树根节点*/
    spinlock_t    tree_lock;/*保护page_tree的自旋锁*/
    unsigned int    i_map_writable;/*VM_SHARED的计数*/
    struct prio_tree_root    i_map;         
    struct list_head    i_map_nonlinear;
    spinlock_t    i_map_lock;/*保护i_map的自旋锁*/
    atomic_t    truncate_count;/*截断计数*/
    unsigned long    nrpages;/*页总数*/
    pgoff_t    writeback_index;/*回写的起始位置*/
    struct address_space_operation*    a_ops;/*操作表*/
    unsigned long    flags;/*gfp_mask掩码与错误标识*/
    struct backing_dev_info*    backing_dev_info;/*预读信息*/
    spinlock_t    private_lock;/*私有address_space锁*/
    struct list_head    private_list;/*私有address_space链表*/
    struct address_space*    assoc_mapping;/*相关的缓冲*/
}
```

进程内存描述符:
```
struct mm_struct {

    //指向线性区对象的链表头
    struct vm_area_struct * mmap;       /* list of VMAs */
    //指向线性区对象的红黑树
    struct rb_root mm_rb;
    //指向最近找到的虚拟区间
    struct vm_area_struct * mmap_cache; /* last find_vma result */

    //用来在进程地址空间中搜索有效的进程地址空间的函数
    unsigned long (*get_unmapped_area) (struct file *filp,
                unsigned long addr, unsigned long len,
                unsigned long pgoff, unsigned long flags);

       unsigned long (*get_unmapped_exec_area) (struct file *filp,
                unsigned long addr, unsigned long len,
                unsigned long pgoff, unsigned long flags);

    //释放线性区时调用的方法，          
    void (*unmap_area) (struct mm_struct *mm, unsigned long addr);

    //标识第一个分配文件内存映射的线性地址
    unsigned long mmap_base;        /* base of mmap area */


    unsigned long task_size;        /* size of task vm space */
    /*
     * RHEL6 special for bug 790921: this same variable can mean
     * two different things. If sysctl_unmap_area_factor is zero,
     * this means the largest hole below free_area_cache. If the
     * sysctl is set to a positive value, this variable is used
     * to count how much memory has been munmapped from this process
     * since the last time free_area_cache was reset back to mmap_base.
     * This is ugly, but necessary to preserve kABI.
     */
    unsigned long cached_hole_size;

    //内核进程搜索进程地址空间中线性地址的空间空间
    unsigned long free_area_cache;      /* first hole of size cached_hole_size or larger */

    //指向页表的目录
    pgd_t * pgd;

    //共享进程时的个数
    atomic_t mm_users;          /* How many users with user space? */

    //内存描述符的主使用计数器，采用引用计数的原理，当为0时代表无用户再次使用
    atomic_t mm_count;          /* How many references to "struct mm_struct" (users count as 1) */

    //线性区的个数
    int map_count;              /* number of VMAs */

    struct rw_semaphore mmap_sem;

    //保护任务页表和引用计数的锁
    spinlock_t page_table_lock;     /* Protects page tables and some counters */

    //mm_struct结构，第一个成员就是初始化的mm_struct结构，
    struct list_head mmlist;        /* List of maybe swapped mm's.  These are globally strung
                         * together off init_mm.mmlist, and are protected
                         * by mmlist_lock
                         */

    /* Special counters, in some configurations protected by the
     * page_table_lock, in other configurations by being atomic.
     */

    mm_counter_t _file_rss;
    mm_counter_t _anon_rss;
    mm_counter_t _swap_usage;

    //进程拥有的最大页表数目
    unsigned long hiwater_rss;  /* High-watermark of RSS usage */、
    //进程线性区的最大页表数目
    unsigned long hiwater_vm;   /* High-water virtual memory usage */

    //进程地址空间的大小，锁住无法换页的个数，共享文件内存映射的页数，可执行内存映射中的页数
    unsigned long total_vm, locked_vm, shared_vm, exec_vm;
    //用户态堆栈的页数，
    unsigned long stack_vm, reserved_vm, def_flags, nr_ptes;
    //维护代码段和数据段
    unsigned long start_code, end_code, start_data, end_data;
    //维护堆和栈
    unsigned long start_brk, brk, start_stack;
    //维护命令行参数，命令行参数的起始地址和最后地址，以及环境变量的起始地址和最后地址
    unsigned long arg_start, arg_end, env_start, env_end;

    
};

```

vm_area_struct,由于每个不同质的虚拟内存区域功能和内部机制都不同，因此一个进程使用多个vm_area_struct结构来分别表示不同类型的虚拟内存区域。
各个vm_area_struct结构使用链表或者树形结构链接，方便进程快速访问
![vm_area_struct](/Users/chris/workspace/xsource/linux/src/main/java/file/images/vm_area_struct.png)
```
struct vm_area_struct {
    //指向vm_mm
	struct mm_struct * vm_mm;	/* The address space we belong to. */
	//该虚拟内存空间的首地址
    unsigned long vm_start;		/* Our start address within vm_mm. */
	//该虚拟内存空间的尾地址
    unsigned long vm_end;		/* The first byte after our end address
					   within vm_mm. */

    //VMA链表的下一个成员
	/* linked list of VM areas per task, sorted by address */
	struct vm_area_struct *vm_next;

	pgprot_t vm_page_prot;		/* Access permissions of this VMA. */
	//保存VMA标志位
    unsigned long vm_flags;		/* Flags, listed below. */
	//将本VMA作为一个节点加入到红黑树中
	struct rb_node vm_rb;
    ...
}

```
![物理页缓存&内存地址空间映射](/Users/chris/workspace/xsource/linux/src/main/java/file/images/mmap.png)


每个进程的地址空间使用mm_struct结构体标识，该结构体中包含一系列的由vm_area_struct结构体组成的连续地址空间链表。
每个vm_area_struct中存在struct  file* vm_file用于指向该连续地址空间中所打开的文件，
而vm_file通过struct file中的struct  path与struct  dentry相关联。  struct dentry中通过inode指针指向inode，
inode与address_space一一对应，至此形成了页缓存与文件系统之间的关联；为了便于查找与某个文件相关联的所有进程，
address_space中的prio_tree_root指向了所有与该页缓存相关联的进程所形成的优先查找树的根节点。
关于这种关系的详细思路请参考图1，这里画出其简化图，如图3。
![](/Users/chris/workspace/xsource/linux/src/main/java/file/images/mmap_struct.png)

##普通文件IO需要复制两次，内存映射文件mmap只需要复制一次，原理分析
[](https://blog.csdn.net/GDJ0001/article/details/80136364)
