package com.ylab.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylab.model.User;
import com.ylab.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class LoginServletTest {

    private LoginServlet loginServlet;
    private UserService userService;

    @BeforeEach
    public void setUp() throws ServletException {
        userService = mock(UserService.class);
        loginServlet = new LoginServlet();
        loginServlet.init();
    }

    @Test
    public void testDoPost_SuccessfulLogin() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletOutputStream outputStream = mock(ServletOutputStream.class);

        String inputJson = "{\"username\":\"testuser\",\"password\":\"testpass\"}";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputJson.getBytes());

        when(request.getInputStream()).thenReturn(new MockServletInputStream(inputStream));
        when(response.getOutputStream()).thenReturn(outputStream);

        User user = new User(1L, "testuser", "testpass", "Test User");
        when(userService.authenticate("testuser", "testpass")).thenReturn(true);
        when(userService.getUser("testuser")).thenReturn(user);

        loginServlet.doPost(request, response);
    }

    @Test
    public void testDoPost_InvalidLogin() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletOutputStream outputStream = mock(ServletOutputStream.class);

        String inputJson = "{\"username\":\"invaliduser\",\"password\":\"invalidpass\"}";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputJson.getBytes());

        when(request.getInputStream()).thenReturn(new MockServletInputStream(inputStream));
        when(response.getOutputStream()).thenReturn(outputStream);

        when(userService.authenticate("invaliduser", "invalidpass")).thenReturn(false);

        loginServlet.doPost(request, response);

    }
}
