package com.ylab.service;

import com.ylab.exception.UserNotFoundException;
import com.ylab.model.User;
import com.ylab.repository.UserRepository;

/**
 * Service for managing users.
 */
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Register a new user.
     *
     * @param user the user to register
     * @throws IllegalArgumentException if the username is already taken
     */
    public void registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Username is already taken");
        }
        userRepository.save(user);
    }

    /**
     * Authenticate a user.
     *
     * @param username the username
     * @param password the password
     * @return true if authentication is successful, false otherwise
     */
    public boolean authenticate(String username, String password) {
        User user;
        try {
            user = userRepository.findByUsername(username);
        } catch (UserNotFoundException e) {
            return false;
        }
        return user.getPassword().equals(password);
    }

    /**
     * Retrieves a user by username.
     *
     * @param username the username to search for
     * @return the user if found, null otherwise
     */
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }
}
