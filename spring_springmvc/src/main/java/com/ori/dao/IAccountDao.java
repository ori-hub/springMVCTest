package com.ori.dao;

import com.ori.domain.AccountBean;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("accountDao")
public interface IAccountDao {

    @Select("select * from account")
    List<AccountBean> findAllAccount();

    @Insert("insert into account(id,name,age) values(#{id},#{name},#{age})")
    int insertAccount(AccountBean accountBean);

    @Update("update account set name=#{name},age=#{age} where id=#{id}")
    int updateAccountById(AccountBean bean);

    @Delete("delete from account where id=#{id}")
    int deleteAccountById(int id);
}
