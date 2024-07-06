package com.ylab.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylab.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class CancelBookingServletTest {

    private CancelBookingServlet cancelBookingServlet;
    private BookingService bookingService;

    @BeforeEach
    public void setUp() throws ServletException {
        bookingService = mock(BookingService.class);
        cancelBookingServlet = new CancelBookingServlet();
        cancelBookingServlet.init();
    }

    @Test
    public void testDoDelete_Success() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletOutputStream outputStream = mock(ServletOutputStream.class);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        when(request.getParameter("id")).thenReturn("1");
        when(response.getOutputStream()).thenReturn(outputStream);
        doAnswer(invocation -> {
            byteArrayOutputStream.write(invocation.getArgument(0));
            return null;
        }).when(outputStream).write(any(byte[].class));

        cancelBookingServlet.doDelete(request, response);

    }

}
