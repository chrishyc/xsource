Introspector
##需求:
1.对javaBean的properties, events, and methods进行自省和赋值
除了通过默认自省方式获取这3个信息，还可以显式声明javaBeanInfo来提供
额外信息.

##方案:
1.隐式自省方式:查看目标类方法是否有这些前缀
static final String ADD_PREFIX = "add";
static final String REMOVE_PREFIX = "remove";
static final String GET_PREFIX = "get";
static final String SET_PREFIX = "set";
static final String IS_PREFIX = "is";

2.显式自省方式:对于目标类Target,自定义TargetBeanInfo implement BeanInfo
实现其中的方法,Introspector会将这些方法的描述信息Descriptor作为额外信息加入
缓存中.

##实现:
###1.关键
创建:
反射目标类,获取Method对象,并封装为MethodDescriptor对象缓存在map中,外部可通过生成的BeanInfo
获取缓存中的MethodDescriptor

获取,ReadMethod,WriteMethod:
通过约定前缀+属性名来获取WriteMethod,ReadMethod来操作目标类中对应的get,set等方法

###2.类型:
工具类:Introspector,ClassFinder
实体类:BeanInfo,GenericBeanInfo,FeatureDescriptor,BeanDescriptor,EventSetDescriptor,
PropertyDescriptor,MethodDescriptor,MethodRef
线程组上下文类:ThreadGroupContext

###3.设计模式:
空模式:private static final Object NULL = new Object();

###4.数据结构

###5.语法:
private constructor
SoftReference
WeakReference
final class ThreadGroupContext
volatile


###6.优化:
WeakCache
map

###7.命名:
Introspector
MethodDescriptor
isPackageAccessible
signature


