##0.什么时候进入转换
set方法注入参数时,requestparam方法注入参数时
##0.转换的条件，找到setPropertyName

##1.什么时候进行,什么时候不进行转换:
a.PropertyEditorRegistrySupport
registerCustomEditor

b.PropertyEditorRegistrySupport
ConversionService

c.方法入参中,输入的参数是方法参数的子类，则不进行默认转换

d.查找默认，未找到则报错

##2.如何知道选择哪个进行转换
默认属性编辑器是根据方法参数的类型

##3.找不到
报错

##4.如何定制?
PropertyEditorRegistrySupport
registerCustomEditor

PropertyEditorRegistrySupport
ConversionService
##5.举例子
http  请求参数name=123,
##6.对象关系,

beanwrapper类包装器

PropertyEditor特定属性编辑器

PropertyEditorRegistrySupport属性处理器中心

BeanPropertyHandler,属性处理器
PropertyDescriptor,属性描述类

TypeConverterDelegate,help类