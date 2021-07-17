package mybatis.start.dao;

import mybatis.start.pojo.Order;

import java.util.List;

public interface IOrderDao {
    List<Order> findAll();
}
