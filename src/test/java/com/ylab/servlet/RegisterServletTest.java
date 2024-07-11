package com.ylab.servlet;

import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class RegisterServletTest {

    @Test
    public void testDoPost_SuccessfulRegistration() throws ServletException, IOException {
        // Mock objects
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletOutputStream outputStream = mock(ServletOutputStream.class);

        // Prepare input JSON
        String inputJson = "{\"username\":\"testuser\",\"password\":\"testpass\",\"name\":\"Test User\"}";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputJson.getBytes());

        // Set up mock behavior for request and response
        when(request.getInputStream()).thenReturn(new MockServletInputStream(inputStream));
        when(response.getOutputStream()).thenReturn(outputStream);

        // Call doPost method
        RegisterServlet servlet = new RegisterServlet();
        servlet.init();
        servlet.doPost(request, response);


    }

}
