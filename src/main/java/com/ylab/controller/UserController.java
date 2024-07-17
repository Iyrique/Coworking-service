package com.ylab.controller;

import com.ylab.dto.UserDTO;
import com.ylab.mapper.UserMapper;
import com.ylab.model.User;
import com.ylab.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Api(value = "User Management System", description = "Operations pertaining to users in User Management System")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    /**
     * Registers a new user.
     *
     * @param userDTO the user data transfer object
     */
    @PostMapping("/register")
    @ApiOperation(value = "Register a new user", response = String.class)
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        try {
            User user = userMapper.toEntity(userDTO);
            userService.registerUser(user);
            return new ResponseEntity<>("User registered successfully.", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Authenticates a user.
     *
     * @param username the username
     * @param password the password
     * @return true if authentication is successful, false otherwise
     */
    @PostMapping("/authenticate")
    @ApiOperation(value = "Authenticate a user", response = Boolean.class)
    public ResponseEntity<Boolean> authenticate(@RequestParam("username") String username, @RequestParam("password") String password) {
        boolean isAuthenticated = userService.authenticate(username, password);
        return new ResponseEntity<>(isAuthenticated, isAuthenticated ? HttpStatus.OK : HttpStatus.UNAUTHORIZED);
    }

    /**
     * Retrieves a user by username.
     *
     * @param username the username
     * @return the user data transfer object
     */
    @GetMapping("/{username}")
    @ApiOperation(value = "Get a user by username", response = UserDTO.class)
    public ResponseEntity<UserDTO> getUser(@PathVariable("username") String username) {
        User user = userService.getUser(username);
        UserDTO userDTO = userMapper.toDto(user);
        return user != null ? new ResponseEntity<>(userDTO, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
