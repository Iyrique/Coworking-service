package com.ylab.repository.impl;

import com.ylab.exception.UserNotFoundException;
import com.ylab.model.User;
import com.ylab.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Repository class for User entity.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private final DataSource dataSource;

    /**
     * Saves a user to the database.
     *
     * @param user the user to save
     */
    public void save(User user) {
        String sql = "INSERT INTO coworking.users (username, password, name) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.executeUpdate();
            log.info("User saved: {}", user.getUsername());
        } catch (SQLException e) {
            log.error("Error saving user", e);
        }
    }

    /**
     * Finds a user by username.
     *
     * @param username the username to search for
     * @return the user if found, null otherwise
     */
    public User findByUsername(String username) throws UserNotFoundException {
        String sql = "SELECT * FROM coworking.users WHERE username = ?";
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(sql);
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User(resultSet.getLong("id"), resultSet.getString("username"),
                            resultSet.getString("password"), resultSet.getString("name"));
                    log.info("User found: {}", username);
                    return user;
                }
            }
        } catch (SQLException e) {
            log.error("Error finding user", e);
        }
        log.info("User not found: {}", username);
        throw new UserNotFoundException("Error finding user");
    }
}
