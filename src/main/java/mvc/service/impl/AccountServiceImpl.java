package mvc.service.impl;

import mvc.mapper.AccountMapper;
import mvc.pojo.Account;
import mvc.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
