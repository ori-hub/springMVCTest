package com.ori.service.impl;

import com.ori.dao.IAccountDao;
import com.ori.domain.AccountBean;
import com.ori.service.IAccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("accountService")
public class AccountServiceImpl implements IAccountService {

    @Resource(name = "accountDao")
    private IAccountDao accountDao;

    @Override
    public List<AccountBean> findAllAccount() {

        List<AccountBean> list = accountDao.findAllAccount();
        return list;
    }

    @Override
    public int insertAccount(AccountBean accountBean) {

        int resultNum = accountDao.insertAccount(accountBean);
        return resultNum;
    }

    @Override
    public int updateAccountById(AccountBean accountBean) {
        int resultNum = accountDao.updateAccountById(accountBean);
        return resultNum;
    }

    @Override
    public int deleteAccountById(int id) {

        int resultNum = accountDao.deleteAccountById(id);
        return resultNum;
    }
}
