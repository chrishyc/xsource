#ASM
##是什么ASM
##事件模型(访问者模式)
[z_02_设计模式_04_访问者模式.md]
##注意事项
###最大数和操作数栈的最大深度计算
[](https://time.geekbang.org/column/article/82761)
```asp
由于本地变量表的最大数和操作数栈的最大深度是在编译时就确定的，所以在使用 ASM 进行字节码操作后需要调用 ASM 提供的 visitMaxs 
方法来设置 maxLocal 和 maxStack 数。不过，ASM 为了方便用户使用，已经提供了自动计算的方法，在实例化 ClassWriter 操作类的时候传入 
COMPUTE_MAXS 后，ASM 就会自动计算本地变量表和操作数栈
```
