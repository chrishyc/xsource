SerializableTypeWrapper
需求:
对四种泛型类实现SerializableTypeProxy接口,Serializable接口，通过proxy方式.
SerializableTypeProxy用于unwrapper序列化。
so,why need Serializable?

类型:工具类，用于wrapper序列化泛型type和unwrapper

实现方案:

关键技术：

数据结构:
ConcurrentReferenceHashMap:softReference
数组

优化:
缓存优化：ConcurrentReferenceHashMap

语法:
final class, 
final field,
private constructor,
方法泛型-通配符类型，
内部类static public修饰符作用？

注解:@Nullable

命名：wrapper,unwrap,provider

api:ObjectUtils.nullSafeHashCode
