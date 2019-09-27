package com.qf.service;

import com.qf.domain.User;

public interface UserService {
    User findByName(String username);

    void register(User user);

    User login(String username, String password);
}
