##查看中断
cat /proc/interrupts,中断详情
cat /proc/stat,查看各中断向量中断总数  
![](.z_操作系统_cpu_常用命令_images/06268746.png)
mpstat,软中断/硬中断占比,  
![](.z_操作系统_cpu_常用命令_images/c2b21bf7.png)
cat /proc/irq,查看中断向量目录
###中断向量表
![](.z_操作系统_cpu_常用命令_images/609aeab8.png)
##查看cpu型号
uname -a
lscpu,查看cpu信息
