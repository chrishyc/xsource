快排：单次快排，每次固定基准值，基准值，左边，右边，中间

经典快排:，大于等于？大于？

https://www.jianshu.com/p/ba2c4e25db32

大于等于，小于等于，选这个，=，=不需要交换

大于等于，小于，可能导致=偏向于一方，性能下降

大于，小于，范围选取，=，=也交换

https://leetcode-cn.com/problems/sort-an-array/solution/dong-hua-mo-ni-yi-ge-kuai-su-pai-xu-wo-x-7n7g/

基准值选取

单边循环
https://blog.csdn.net/Xidian2850/article/details/90725120

双边循环

纵向优化，横向优化

二路

https://segmentfault.com/a/1190000021726667

三路

https://leetcode-cn.com/problems/sort-an-array/solution/dong-hua-mo-ni-yi-ge-kuai-su-pai-xu-wo-x-7n7g/


迭代方式

jdk快排

外层排序<=  选<=时就需要使用中间，且不能交换尾部,   nums[i]<nums[j],
外层排序<   尾部还需要交换,  nums[i]<=nums[j]选<=,=就不需要交换了

基准交换

死循环，越界，性能优化


每轮确定一个位置，分开<,>,等于的可能左边右边都有，尾部交换越界问题

https://blog.csdn.net/hacker00011000/article/details/52176100

循环不变量
