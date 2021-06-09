package start.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Order implements Serializable {
    private int uid;
    private Date ordertime;
    private double total;
}
