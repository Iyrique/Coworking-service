package com.ylab.service;

import com.ylab.model.User;

public interface UserService {

    void registerUser(User user);

    boolean authenticate(String username, String password);

    User getUser(String username);
}
