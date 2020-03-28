package demo.sqlSession;


import demo.pojo.Configuration;
import demo.pojo.MappedStatement;

import java.util.List;

public interface Executor {

    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;

}
