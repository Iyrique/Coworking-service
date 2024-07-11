package com.ylab.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylab.model.User;
import com.ylab.repository.impl.UserRepositoryImpl;
import com.ylab.service.impl.UserServiceImpl;
import lombok.Data;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private UserServiceImpl userServiceImpl;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        userServiceImpl = new UserServiceImpl(new UserRepositoryImpl());
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoginRequest loginRequest = objectMapper.readValue(req.getInputStream(), LoginRequest.class);
        if (userServiceImpl.authenticate(loginRequest.getUsername(), loginRequest.getPassword())) {
            User user = userServiceImpl.getUser(loginRequest.getUsername());
            resp.setStatus(HttpServletResponse.SC_OK);
            objectMapper.writeValue(resp.getOutputStream(), user);
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            objectMapper.writeValue(resp.getOutputStream(), "Invalid username or password");
        }
    }

    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }
}
