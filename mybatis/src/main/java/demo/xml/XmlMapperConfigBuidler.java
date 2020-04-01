package demo.xml;

import demo.pojo.Configuration;
import demo.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlMapperConfigBuidler {

    private Configuration configuration;

    public XmlMapperConfigBuidler(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration build(InputStream inputStream) throws DocumentException {
        Document document = new SAXReader().read(inputStream);
        Element root = document.getRootElement();
        String namespace = root.attributeValue("namespace");
        List<Element> list = new ArrayList<>();
        List<Element> selects = root.elements("select");
        List<Element> inserts = root.elements("insert");
        List<Element> updates = root.elements("update");
        List<Element> deletes = root.elements("delete");

        list.addAll(selects);
        list.addAll(inserts);
        list.addAll(updates);
        list.addAll(deletes);
        for (Element element : list) {
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String paramterType = element.attributeValue("paramterType");
            String sqlText = element.getTextTrim();
            MappedStatement statement = new MappedStatement();
            statement.setId(id);
            statement.setResultType(resultType);
            statement.setParamterType(paramterType);
            statement.setSql(sqlText);
            String key = namespace + "." + id;
            configuration.getMappedStatementMap().put(key, statement);
        }
        return configuration;
    }
}
