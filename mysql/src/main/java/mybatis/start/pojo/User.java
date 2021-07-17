package mybatis.start.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class User implements Serializable {

    private Integer id;
    private String username;
    private String password;
    private Date birthday;
    private List<Order> orderList;
}
