package demo.sqlSession;

import demo.io.Resources;
import demo.pojo.Configuration;
import demo.xml.XmlSQLConfigBuilder;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

public class SqlSessionFactoryBuilder {

    public DefaultSqlSessionFactory build() throws DocumentException, PropertyVetoException {
        // 第一：使用dom4j解析配置文件，将解析出来的内容封装到Configuration中
        InputStream in = Resources.getInputStream("sqlMapConfig.xml");
        XmlSQLConfigBuilder xmlConfigBuilder = new XmlSQLConfigBuilder();
        Configuration configuration = xmlConfigBuilder.build(in);


        // 第二：创建sqlSessionFactory对象：工厂类：生产sqlSession:会话对象
        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);

        return defaultSqlSessionFactory;
    }


}
