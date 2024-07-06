package com.ylab.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylab.dto.BookingDTO;
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

@WebServlet("/bookWorkspace")
public class BookWorkspaceServlet extends HttpServlet {

    private final BookingService bookingService;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    public BookWorkspaceServlet() {
        this.bookingService = new BookingService(new BookingRepository());
        this.objectMapper = new ObjectMapper();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BookingDTO bookingDTO = objectMapper.readValue(req.getInputStream(), BookingDTO.class);
        Set<ConstraintViolation<BookingDTO>> violations = validator.validate(bookingDTO);

        if (!violations.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), violations);
            return;
        }

        Booking booking = new Booking(null, bookingDTO.getUsername(), bookingDTO.getResourceId(),
                bookingDTO.getStartTime(), bookingDTO.getEndTime(), bookingDTO.isWorkspace());
        try {
            bookingService.bookResource(booking);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            objectMapper.writeValue(resp.getWriter(), "Workspace booked successfully");
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            objectMapper.writeValue(resp.getWriter(), "Time slot is already booked");
        }
    }
}
