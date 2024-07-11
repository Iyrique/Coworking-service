package com.ylab.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylab.repository.impl.BookingRepositoryImpl;
import com.ylab.service.impl.BookingServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CancelBookingServlet", urlPatterns = {"/cancelBooking"})
public class CancelBookingServlet extends HttpServlet {

    private BookingServiceImpl bookingServiceImpl;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        bookingServiceImpl = new BookingServiceImpl(new BookingRepositoryImpl());
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int bookingId = Integer.parseInt(req.getParameter("id"));
        bookingServiceImpl.cancelBooking(bookingId);
        resp.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(resp.getOutputStream(), "Booking cancelled successfully.");
    }
}
