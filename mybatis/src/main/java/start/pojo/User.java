package start.pojo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class User {

    private Integer id;
    private String username;
    private String password;
    private Date birthday;
    private List<Order> orderList;
}
