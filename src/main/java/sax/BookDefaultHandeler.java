package sax;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class BookDefaultHandeler extends DefaultHandler {
    //重写第一个方法

    /**
     * 解析xml文档开始时调用
     */
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        System.out.println("解析XML文档开始");
    }

    /**
     * 解析xml文档结束时调用
     */
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        System.out.println("解析XML文档结束");
    }

    /**
     * 解析XML文档中的节点时调用
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        System.out.println("解析xml文档中节点时调用");
        /**判断，如果是book节点，获取节点的属性和属性值*/
        if ("book".equals(qName)) {
            //获取所有的属性
            int count = attributes.getLength();//属性的个数
            //循环获取每个属性
            for (int i = 0; i < count; i++) {
                String attName = attributes.getQName(i);//属性名称
                String attValue = attributes.getValue(i);//属性值
                System.out.println("属性名称：" + attName + "\t属性值为:" + attValue);
            }
        } else if (!"books".equals(qName) && !"book".equals(qName)) {
            System.out.print("节点的名称:" + qName + "\t");
        }
    }

    //获取节点文本
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        String value = new String(ch, start, length);
        if (!"".equals(value.trim())) {
            System.out.println(value);
        }
    }

    /**
     * 解析XML文档的节点结束时调用
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        System.out.println("解析xml文档中的节点结束时调用");
    }
}
