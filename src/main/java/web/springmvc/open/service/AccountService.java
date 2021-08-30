package web.springmvc.open.service;


import web.springmvc.open.pojo.Account;

import java.util.List;

public interface AccountService {
    List<Account> queryAccountList() throws Exception;
}
