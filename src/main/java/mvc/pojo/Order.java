package mvc.pojo;

import lombok.Data;

@Data
public class Order {
    private long orderId;
    private User user;
}
