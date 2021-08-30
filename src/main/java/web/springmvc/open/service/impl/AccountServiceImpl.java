package web.springmvc.open.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.springmvc.open.mapper.AccountMapper;
import web.springmvc.open.pojo.Account;
import web.springmvc.open.service.AccountService;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;
    
    @Override
    public List<Account> queryAccountList() throws Exception {
        return accountMapper.queryAccountList();
    }
}
