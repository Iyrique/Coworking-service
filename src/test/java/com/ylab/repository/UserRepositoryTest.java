package com.ylab.repository;

import com.ylab.connector.ConnectorDB;
import com.ylab.exception.UserNotFoundException;
import com.ylab.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UserRepository.
 */
@Testcontainers
public class UserRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryTest.class);

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    private static UserRepository userRepository;

    @BeforeAll
    public static void setUp() throws SQLException {
        ConnectorDB.setConnectionDetails(
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword()
        );

        try (Connection connection = DriverManager.getConnection(
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword()
        )) {
            connection.createStatement().execute("CREATE SCHEMA coworking");
            connection.createStatement().execute(
                    "CREATE TABLE coworking.users (" +
                            "id SERIAL PRIMARY KEY, " +
                            "username VARCHAR(50) UNIQUE NOT NULL, " +
                            "password VARCHAR(50) NOT NULL, " +
                            "name VARCHAR(50) NOT NULL)"
            );
            logger.info("Schema and table created.");
        }

        userRepository = new UserRepository();
    }

    @Test
    @DisplayName("Test saving a user and finding by username")
    public void testSaveAndFindByUsername() {
        User user = new User(null, "testuser", "testpass", "Test User");
        userRepository.save(user);

        User retrievedUser = userRepository.findByUsername("testuser");
        assertNotNull(retrievedUser);
        assertEquals("testuser", retrievedUser.getUsername());
        assertEquals("testpass", retrievedUser.getPassword());
        assertEquals("Test User", retrievedUser.getName());
    }

    @Test
    @DisplayName("Test finding a user by username when user does not exist")
    public void testFindByUsernameNotFound() {
        assertThrows(UserNotFoundException.class, () -> userRepository.findByUsername("nonexistentuser"));
    }
}
