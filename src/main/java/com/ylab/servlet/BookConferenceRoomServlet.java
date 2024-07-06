package com.ylab.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylab.dto.BookingDTO;
import com.ylab.mapper.BookingMapper;
import com.ylab.model.Booking;
import com.ylab.repository.BookingRepository;
import com.ylab.service.BookingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.*;
import java.io.IOException;
import java.util.Set;

@WebServlet("/bookConferenceRoom")
public class BookConferenceRoomServlet extends HttpServlet {

    private final BookingService bookingService;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    public BookConferenceRoomServlet() {
        this.bookingService = new BookingService(new BookingRepository());
        this.objectMapper = new ObjectMapper();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            BookingDTO bookingRequest = objectMapper.readValue(req.getInputStream(), BookingDTO.class);
            Set<ConstraintViolation<BookingDTO>> violations = validator.validate(bookingRequest);

            if (!violations.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                objectMapper.writeValue(resp.getOutputStream(), violations);
                return;
            }

            Booking booking = new Booking(null, bookingRequest.getUsername(), bookingRequest.getResourceId(),
                    bookingRequest.getStartTime(), bookingRequest.getEndTime(), bookingRequest.isWorkspace());

            bookingService.bookResource(booking);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(e.getMessage());
        }
    }
}
