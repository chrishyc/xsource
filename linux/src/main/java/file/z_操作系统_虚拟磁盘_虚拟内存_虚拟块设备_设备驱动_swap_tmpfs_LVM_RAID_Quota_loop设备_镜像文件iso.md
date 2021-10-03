##临界知识
物理硬件-虚拟(软件)硬件(使用物理硬件的魔数模拟硬件)
磁盘容量不断增大-文件系统更迭
分区容量动态扩容(磁盘/分区虚拟化)-lvm
不同用户磁盘容量管理-quota
数据安全性策略-磁盘阵列
访问效率优化-文件系统
数据版本管理-lvm快照
##虚拟块设备
###内存模拟
###文件模拟,loop设备


使用文件来模拟块设备的一种技术，文件模拟成块设备后, 就像一个磁盘或光盘一样使用
在使用之前，一个 loop 设备必须要和一个文件进行连接。  
这种结合方式给用户提供了一个替代块特殊文件的接口。因此，如果这个文件包含有一个完整的文件系统，  
那么这个文件就可以像一个磁盘设备一样被 mount 起来。之所以叫loop设备（回环），  
其实是从文件系统这一层来考虑的，因为这种被 mount 起来的镜像文件它本身也包含有文件系统，  
通过loop设备把它mount起来，它就像是文件系统之上再绕了一圈的文件系统，所以称为 loop

![](.z_操作系统_虚拟磁盘_虚拟内存_虚拟块设备_swap_tmpfs_LVM_RAID_Quota_loop设备_images/a8cd096f.png)
###iso镜像文件
通常将iso镜像文件挂载为loop设备使用
##tmpfs
tmpfs=(Real Memory)+(swap)
tmpfs使用的是VM，因此它比硬盘的速度肯定要快
##虚拟内存
###swap
Swap是硬盘存储,通过硬盘虚拟出来的内存空间
![](.z_操作系统_虚拟磁盘_虚拟内存_swap_tmpfs_LVM_RAID_Quota_images/b1a0a5fa.png)
![](.z_操作系统_虚拟磁盘_虚拟内存_swap_tmpfs_LVM_RAID_Quota_images/15079606.png)
因为 swap 主要的功能是当实体内存不够时，则某些在内存当中所占的程序会暂时被移动到 swap 当中，  
让实体内存可以被需要的程序来使用。另外，如果你的主机支持电源管理模式， 也就是说，你的 Linux 主机系统可以进入“休眠”模式的话，  
那么， 运行当中的程序状态则会被 纪录到 swap 去，以作为“唤醒”主机的状态依据!
##quota磁盘配额
![](.z_操作系统_虚拟磁盘_虚拟内存_swap_tmpfs_LVM_RAID_Quota_images/6c9855e2.png)
###上下文需求
对不同用户/用户组/目录的磁盘容量进行隔离管理
###方案
quota
![](.z_操作系统_虚拟磁盘_虚拟内存_swap_tmpfs_LVM_RAID_Quota_images/215d61cf.png)
###输出
查看配额
repquota -v /home
##磁盘阵列
###临界知识
通过一个技术(软件或硬件)，将多个较小的磁盘整合成为一个 较大的磁盘设备
###需求
```asp
1. 数据安全与可靠性:指的并非网络信息安全，而是当硬件(指磁盘)损毁时，数据是否 还能够安全的救援或使用之意;
2. 读写性能:例如RAID0可以加强读写性能，让你的系统I/O部分得以改善; 
3. 容量:可以让多颗磁盘组合起来，故单一文件系统可以有相当大的容量。
```
###方案
####临界知识
容量扩充,访问(读写)效率,数据可用性评估
####RAID-0 (等量模式,
每个磁盘会 交错的存放数据， 因此当你的数据要写入 RAID 时，数据会被等量的放置在各个磁盘上面
![](.z_操作系统_虚拟磁盘_虚拟内存_swap_tmpfs_LVM_RAID_Quota_images/ac7ff098.png)
访问效率增强
数据可用性下降
####RAID-1 (映射模式, mirror):完整备份
让同一份数据，完 整的保存在两颗磁盘上头
![](.z_操作系统_虚拟磁盘_虚拟内存_swap_tmpfs_LVM_RAID_Quota_images/6f01c413.png)
写效率下降,读不变
数据可用性增强
####RAID 0+1
RAID-0 的性能佳但是数据不安全，RAID-1 的数据安全但是性能不佳，那么能不能将这两者 整合起来设置 RAID 
![](.z_操作系统_虚拟磁盘_虚拟内存_swap_tmpfs_LVM_RAID_Quota_images/a99dc994.png)
![](.z_操作系统_虚拟磁盘_虚拟内存_swap_tmpfs_LVM_RAID_Quota_images/7bab1989.png)
###软件磁盘阵列
硬件磁盘阵列在 Linux 下面看起来就是一颗实际的大磁盘，因此硬 件磁盘阵列的设备文件名为 /dev/sd[a-p] ，因为使用到 SCSI 的模块之故。至于软件磁盘阵列 则是系统仿真的，  
因此使用的设备文件名是系统的设备文件， 文件名为 /dev/md0, /dev/md1...，两者的设备文件名并不相

##LVM逻辑卷轴管理
![](.z_操作系统_虚拟磁盘_虚拟内存_swap_tmpfs_LVM_RAID_Quota_images/10e016ec.png)
###临界知识
分区容量动态扩容
PV(Physical Volume, PV, 实体卷轴)    
![](.z_操作系统_虚拟磁盘_虚拟内存_swap_tmpfs_LVM_RAID_Quota_images/73dca471.png)  
VG(Volume Group,  卷轴群组)  
![](.z_操作系统_虚拟磁盘_虚拟内存_swap_tmpfs_LVM_RAID_Quota_images/17eeb198.png)  
LV,(Logical Volume, LV, 逻辑卷轴)  
![](.z_操作系统_虚拟磁盘_虚拟内存_swap_tmpfs_LVM_RAID_Quota_images/94584344.png)
PE(Physical Extent, PE, 实体范围区块)

![](.z_操作系统_虚拟磁盘_虚拟内存_swap_tmpfs_LVM_RAID_Quota_images/850964e4.png)
![](.z_操作系统_虚拟磁盘_虚拟内存_swap_tmpfs_LVM_RAID_Quota_images/b45d0b5e.png)
###需求上下文
可以弹性的调整 filesystem 的容量,LVM 最主要的用处是在实现一个可以弹性调整容量的文件系统上
###方案

###输出
![](.z_操作系统_虚拟磁盘_虚拟内存_swap_tmpfs_LVM_RAID_Quota_images/39d5362f.png)
查看lvm
![](.z_操作系统_虚拟磁盘_虚拟内存_swap_tmpfs_LVM_RAID_Quota_images/0ad939bb.png)
##磁盘快照
###临界知识
快照就是将当时的系统 信息记录下来，就好像照相记录一般! 未来若有任何数据更动了，则原始数据会被搬移到快 照区，没有被更动的区域则由快照区与文件系统共享
![](.z_操作系统_虚拟磁盘_虚拟内存_swap_tmpfs_LVM_RAID_Quota_images/941aa350.png)
###需求上下文
磁盘数据版本管理
###方案
###输出
