```
<?xml version="1.0"?>

<note xmlns="http://www.w3school.com.cn"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://www.w3school.com.cn note.xsd">

    <to>George</to>
    <from>John</from>
    <heading>Reminder</heading>
    <body>Don't forget the meeting!</body>
</note>
```
XML 命名空间属性被放置于元素的开始标签之中，并使用以下的语法:xmlns:namespace-prefix="namespaceURI"


xmlns="http://www.w3school.com.cn" 规定了默认命名空间的声明。  
此声明会告知 schema 验证器，在此 XML 文档中使用的所有元素都被声明于 "http://www.w3school.com.cn" 这个命名空间。  



xmlns:xsi 在不同的 xml 文档中似乎都会出现。这是因为， xsi 已经成为了一个业界默认的用于   
XSD(（XML Schema Definition) 文件的命名空间。
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 


xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd  
xsi:schemaLocation = "键" “值”
前一个“键” http://www.springframework.org/schema/beans 指代 【命名空间】  
后一个是xsd文件  http://www.springframework.org/schema/beans/spring-beans.xsd  
![](http://www.springframework.org/schema/beans)

