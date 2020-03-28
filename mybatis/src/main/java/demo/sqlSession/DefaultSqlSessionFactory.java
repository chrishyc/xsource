package demo.sqlSession;

import demo.pojo.Configuration;

public class DefaultSqlSessionFactory {
    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    public DefaultSqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
