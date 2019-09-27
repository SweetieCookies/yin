package com.qf.service.impl;

import com.qf.dao.UserDao;
import com.qf.domain.User;
import com.qf.service.UserService;
import com.qf.utils.EmailUtils;
import com.qf.utils.Md5Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User findByName(String username) {
        return userDao.findByName(username);
    }

    @Override
    public void register(User user) {
        //Md5加密
        String password = Md5Utils.md5(user.getPassword());
        user.setPassword(password);
        EmailUtils.sendEmail(user);
        userDao.add(user);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User login(String username, String password) {
        //将密码加密后在比较
        password = Md5Utils.md5(password);
        return userDao.findByUserNameAndPassWord(username, password);
    }
}
