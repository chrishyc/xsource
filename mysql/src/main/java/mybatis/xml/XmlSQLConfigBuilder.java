package mybatis.xml;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import mybatis.io.Resources;
import mybatis.pojo.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XmlSQLConfigBuilder {

    private Configuration configuration;

    public XmlSQLConfigBuilder() {
        this.configuration = new Configuration();
    }

    public Configuration build(InputStream inputStream) throws DocumentException, PropertyVetoException {
        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();
        Element dataSource = rootElement.element("dataSource");
        List<Element> list = dataSource.elements("property");

        Properties properties = new Properties();
        for (Element element : list) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name, value);
        }
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));
        configuration.setDataSource(comboPooledDataSource);
        List<Element> mapperList = rootElement.elements("mapper");
        for (Element element : mapperList) {
            String mapperPath = element.attributeValue("resource");
            InputStream resourceAsSteam = Resources.getInputStream(mapperPath);
            configuration = new XmlMapperConfigBuidler(configuration).build(resourceAsSteam);
        }
        return configuration;
    }
}
