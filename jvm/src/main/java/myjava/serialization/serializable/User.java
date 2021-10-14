package myjava.serialization.serializable;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private String userName;
    private String password;
    private String sex;

    public User(String userName, String password, String sex) {
        this.userName = userName;
        this.password = password;
        this.sex = sex;
    }
}
