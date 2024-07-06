package com.ylab.service;

import com.ylab.exception.UserNotFoundException;
import com.ylab.model.User;
import com.ylab.repository.UserRepository;
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
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    @DisplayName("Register new user successfully")
    void registerUserSuccessfully() {
        User newUser = new User(null, "test1user", "password", "Test User");
        when(userRepository.findByUsername("testuser")).thenReturn(null);

        assertDoesNotThrow(() -> userService.registerUser(newUser));
//        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    @DisplayName("Register user with existing username")
    void registerUserWithExistingUsername() {
        User existingUser = new User(1L, "testuser", "password", "Test User");
        when(userRepository.findByUsername("testuser")).thenReturn(existingUser);

        User newUser = new User(null, "testuser", "password", "New User");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.registerUser(newUser));
        assertEquals("Username is already taken", exception.getMessage());
        verify(userRepository, times(0)).save(newUser);
    }

    @Test
    @DisplayName("Authenticate user successfully")
    void authenticateUserSuccessfully() {
        User existingUser = new User(1L, "testuser", "password", "Test User");
        when(userRepository.findByUsername("testuser")).thenReturn(existingUser);

        assertTrue(userService.authenticate("testuser", "password"));
    }

    @Test
    @DisplayName("Fail to authenticate user with wrong password")
    void failToAuthenticateWithWrongPassword() {
        User existingUser = new User(1L, "testuser", "password", "Test User");
        when(userRepository.findByUsername("testuser")).thenReturn(existingUser);

        assertFalse(userService.authenticate("testuser", "wrongpassword"));
    }

    @Test
    @DisplayName("Fail to authenticate non-existing user")
    void failToAuthenticateNonExistingUser() {
        when(userRepository.findByUsername("nonexistentuser")).thenThrow(UserNotFoundException.class);

        assertFalse(userService.authenticate("nonexistentuser", "password"));
    }

    @Test
    @DisplayName("Get user by username")
    void getUserByUsername() {
        User existingUser = new User(1L, "testuser", "password", "Test User");
        when(userRepository.findByUsername("testuser")).thenReturn(existingUser);

        User foundUser = userService.getUser("testuser");
        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
        assertEquals("Test User", foundUser.getName());
    }

    @Test
    @DisplayName("Get non-existing user by username")
    void getNonExistingUserByUsername() {
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(null);

        User foundUser = userService.getUser("nonexistentuser");
        assertNull(foundUser);
    }
}
