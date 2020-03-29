package start.pojo;

import demo.pojo.User;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    private long uid;
    private Date ordertime;
    private double total;
    private User user;
}
