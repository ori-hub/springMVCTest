package com.ori.service;

import com.ori.domain.AccountBean;

import java.util.List;

public interface IAccountService {

    List<AccountBean> findAllAccount();
    int insertAccount(AccountBean accountBean);
    int updateAccountById(AccountBean accountBean);
    int deleteAccountById(int id);
}
