package com.qf.dao;

import com.qf.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
    User findByName(String username);

    void add(User user);

    User findByUserNameAndPassWord(@Param("username") String username,@Param("password") String password);
}
