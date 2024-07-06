package com.ylab.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylab.repository.BookingRepository;
import com.ylab.service.BookingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CancelBookingServlet", urlPatterns = {"/cancelBooking"})
public class CancelBookingServlet extends HttpServlet {

    private BookingService bookingService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        bookingService = new BookingService(new BookingRepository());
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int bookingId = Integer.parseInt(req.getParameter("id"));
        bookingService.cancelBooking(bookingId);
        resp.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(resp.getOutputStream(), "Booking cancelled successfully.");
    }
}
