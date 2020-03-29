package start.dao;

import start.pojo.Order;

import java.util.List;

public interface IOrderDao {
    List<Order> findAll();
}
