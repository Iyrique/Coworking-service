package com.ylab.service;

import com.ylab.exception.UserNotFoundException;
import com.ylab.model.User;
import com.ylab.repository.impl.UserRepositoryImpl;
import com.ylab.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for UserService.
 */
class UserServiceTest {
    private UserRepositoryImpl userRepositoryImpl;
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        userRepositoryImpl = Mockito.mock(UserRepositoryImpl.class);
        userServiceImpl = new UserServiceImpl(userRepositoryImpl);
    }

    @Test
    @DisplayName("Register new user successfully")
    void registerUserSuccessfully() {
        User newUser = new User(null, "test1user", "password", "Test User");
        when(userRepositoryImpl.findByUsername("testuser")).thenReturn(null);

        assertDoesNotThrow(() -> userServiceImpl.registerUser(newUser));
    }

    @Test
    @DisplayName("Register user with existing username")
    void registerUserWithExistingUsername() {
        User existingUser = new User(1L, "testuser", "password", "Test User");
        when(userRepositoryImpl.findByUsername("testuser")).thenReturn(existingUser);

        User newUser = new User(null, "testuser", "password", "New User");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userServiceImpl.registerUser(newUser));
        assertEquals("Username is already taken", exception.getMessage());
    }

    @Test
    @DisplayName("Authenticate user successfully")
    void authenticateUserSuccessfully() {
        User existingUser = new User(1L, "testuser", "password", "Test User");
        when(userRepositoryImpl.findByUsername("testuser")).thenReturn(existingUser);

        assertTrue(userServiceImpl.authenticate("testuser", "password"));
    }

    @Test
    @DisplayName("Fail to authenticate user with wrong password")
    void failToAuthenticateWithWrongPassword() {
        User existingUser = new User(1L, "testuser", "password", "Test User");
        when(userRepositoryImpl.findByUsername("testuser")).thenReturn(existingUser);

        assertFalse(userServiceImpl.authenticate("testuser", "wrongpassword"));
    }

    @Test
    @DisplayName("Fail to authenticate non-existing user")
    void failToAuthenticateNonExistingUser() {
        when(userRepositoryImpl.findByUsername("nonexistentuser")).thenThrow(UserNotFoundException.class);

        assertFalse(userServiceImpl.authenticate("nonexistentuser", "password"));
    }

    @Test
    @DisplayName("Get user by username")
    void getUserByUsername() {
        User existingUser = new User(1L, "testuser", "password", "Test User");
        when(userRepositoryImpl.findByUsername("testuser")).thenReturn(existingUser);

        User foundUser = userServiceImpl.getUser("testuser");
        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
        assertEquals("Test User", foundUser.getName());
    }

    @Test
    @DisplayName("Get non-existing user by username")
    void getNonExistingUserByUsername() {
        when(userRepositoryImpl.findByUsername("nonexistentuser")).thenReturn(null);

        User foundUser = userServiceImpl.getUser("nonexistentuser");
        assertNull(foundUser);
    }
}
