package web.springmvc.open.mapper;


import web.springmvc.open.pojo.Account;

import java.util.List;

/**
 * @author chris
 */
public interface AccountMapper {
    // 定义dao层接口方法--> 查询account表所有数据
    List<Account> queryAccountList() throws Exception;
}
