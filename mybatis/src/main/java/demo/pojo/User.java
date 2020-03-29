package demo.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author chris
 */
@Data
public class User {

    private Integer id;
    private String username;
    private String password;
    private Date birthday;
}
