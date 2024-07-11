package com.ylab.repository;

import com.ylab.exception.UserNotFoundException;
import com.ylab.model.User;

public interface UserRepository {

    void save(User user);

    User findByUsername(String username) throws UserNotFoundException;
}
