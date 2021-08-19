package dom;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class TestDOMParse {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        //1、创建一个DocumentBuilderFactory的对象
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        //2、创建一个DocumentBuilder对象
        DocumentBuilder db = dbf.newDocumentBuilder();
        //3、通过DocumentBuilder的parse（...）方法得到Document对象
        Document doc = db.parse("/Users/chris/byteclass/src/main/resources/book.xml");
        //4、通过getElementsByTagName(...)方法获取到节点的列表
        NodeList booklist = doc.getElementsByTagName("book");
        System.out.println(booklist.getLength());
        //5、通过for循环遍历每一个节点
        for (int i = 0; i < booklist.getLength(); ++i) {
            //6、得到每个节点的属性和属性值
            Node book = booklist.item(i);
            NamedNodeMap attrs = book.getAttributes();//得到属性的集合
            //循环遍历每一个属性
            for (int j = 0; j < attrs.getLength(); j++) {
                //得到每一个属性
                Node id = attrs.item(j);
                System.out.println("属性的名称：" + id.getNodeName() + "\t" + id.getNodeValue());
            }
        }
        System.out.println("\n每个节点的名和节点的值");
        //7、得到每个节点的节点名和节点值
        for (int i = 0; i < booklist.getLength(); ++i) {
            //得到每一个Book节点
            Node book = booklist.item(i);
            NodeList subNode = book.getChildNodes();
            System.out.println("字节点的个数：" + subNode.getLength());
            //使用for循环遍历每一个book 的子节点
            for (int j = 0; j < subNode.getLength(); j++) {
                Node childNode = subNode.item(j);
//				System.out.println(childNode.getNodeName());
                short type = childNode.getNodeType();//获取节点的类型
                if (type == Node.ELEMENT_NODE) {
                    System.out.println("节点的名称：" + childNode.getNodeName() + "\t" + childNode.getTextContent());
                }
            }
        }
    }
}
