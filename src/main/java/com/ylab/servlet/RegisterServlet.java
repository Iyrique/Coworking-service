package com.ylab.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylab.dto.UserDTO;
import com.ylab.model.User;
import com.ylab.repository.impl.UserRepositoryImpl;
import com.ylab.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    private UserServiceImpl userServiceImpl;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        userServiceImpl = new UserServiceImpl(new UserRepositoryImpl());
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO userDTO = objectMapper.readValue(req.getInputStream(), UserDTO.class);
        try {
            User user = new User(null, userDTO.getUsername(), userDTO.getPassword(), userDTO.getName());
            userServiceImpl.registerUser(user);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            objectMapper.writeValue(resp.getOutputStream(), "Registration successful. You can now log in.");
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            objectMapper.writeValue(resp.getOutputStream(), "Username already exists.");
        }
    }
}
