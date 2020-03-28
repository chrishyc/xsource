package demo.pojo;

import lombok.Data;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Data
public class Configuration {

    private DataSource dataSource;
    private Map<String, MappedStatement> mappedStatementMap = new HashMap<>();

}
