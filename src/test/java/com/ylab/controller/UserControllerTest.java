package com.ylab.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylab.dto.UserDTO;
import com.ylab.mapper.UserMapper;
import com.ylab.model.User;
import com.ylab.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @Test
    @DisplayName("Test user registration")
    public void testRegisterUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        User user = new User();

        when(userMapper.toEntity(any(UserDTO.class))).thenReturn(user);

        String userJson = objectMapper.writeValueAsString(userDTO);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("User registered successfully."));

        verify(userService, times(1)).registerUser(any(User.class));
    }

    @Test
    @DisplayName("Test user registration with exception")
    public void testRegisterUserException() throws Exception {
        UserDTO userDTO = new UserDTO();

        when(userMapper.toEntity(any(UserDTO.class))).thenThrow(new IllegalArgumentException("Invalid user data"));

        String userJson = objectMapper.writeValueAsString(userDTO);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid user data"));
    }

    @Test
    @DisplayName("Test user authentication")
    public void testAuthenticate() throws Exception {
        String username = "testuser";
        String password = "password";

        when(userService.authenticate(username, password)).thenReturn(true);

        mockMvc.perform(post("/api/users/authenticate")
                        .param("username", username)
                        .param("password", password))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(userService, times(1)).authenticate(username, password);
    }

    @Test
    @DisplayName("Test user authentication failure")
    public void testAuthenticateFailure() throws Exception {
        String username = "testuser";
        String password = "wrongpassword";

        when(userService.authenticate(username, password)).thenReturn(false);

        mockMvc.perform(post("/api/users/authenticate")
                        .param("username", username)
                        .param("password", password))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("false"));

        verify(userService, times(1)).authenticate(username, password);
    }

    @Test
    @DisplayName("Test get user by username")
    public void testGetUser() throws Exception {
        String username = "testuser";
        User user = new User();
        UserDTO userDTO = new UserDTO();

        when(userService.getUser(username)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDTO);

        mockMvc.perform(get("/api/users/{username}", username))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

        verify(userService, times(1)).getUser(username);
    }

    @Test
    @DisplayName("Test get user by username not found")
    public void testGetUserNotFound() throws Exception {
        String username = "unknownuser";

        when(userService.getUser(username)).thenReturn(null);

        mockMvc.perform(get("/api/users/{username}", username))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).getUser(username);
    }
}
