package sax;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class TestSAXParse {
    public static void main(String[] args) throws ParserConfigurationException, org.xml.sax.SAXException, IOException {
        //1、创建SAXParseFactory的对象
        SAXParserFactory spf = SAXParserFactory.newInstance();
        //2、创建SAXParse对象（解析器）
        SAXParser parser = spf.newSAXParser();
        //3、创建一个DefaultHandler的子类
        BookDefaultHandeler bdh = new BookDefaultHandeler();
        //4、调用parse方法
        parser.parse("/Users/chris/byteclass/src/main/resources/book_sax.xml", bdh);
    }
}
