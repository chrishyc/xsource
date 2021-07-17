package mybatis.sqlSession;


import mybatis.pojo.Configuration;
import mybatis.pojo.MappedStatement;

import java.util.List;

public interface Executor {

    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;

    public int update(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;
}
