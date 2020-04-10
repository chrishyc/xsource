<!ELEMENT note (to, from, heading, body)>
<!ELEMENT to (#PCDATA)>
<!ELEMENT from (#PCDATA)>
<!ELEMENT heading (#PCDATA)>
<!ELEMENT body (#PCDATA)>


<?xml version="1.0"?>
<note>
    <to>George</to>
    <from>John</from>
    <heading>Reminder</heading>
    <body>Don't forget the meeting!</body>
</note>

<!--第 1 行定义 note 元素有四个子元素："to, from, heading, body"。

第 2-5 行定义了 to, from, heading, body 元素的类型是 "#PCDATA"。-->
