package com.ylab.controller;

import com.ylab.dto.BookingDTO;
import com.ylab.mapper.BookingMapper;
import com.ylab.model.Booking;
import com.ylab.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    @Autowired
    public BookingController(BookingService bookingService, BookingMapper bookingMapper) {
        this.bookingService = bookingService;
        this.bookingMapper = bookingMapper;
    }

    /**
     * Books a workspace or conference room.
     *
     * @param bookingDTO the booking data transfer object
     */
    @PostMapping
    public ResponseEntity<String> bookResource(@RequestBody BookingDTO bookingDTO) {
        Booking booking = bookingMapper.toEntity(bookingDTO);
        bookingService.bookResource(booking);
        return new ResponseEntity<>("Booking created successfully.", HttpStatus.CREATED);
    }

    /**
     * Cancels a booking by ID.
     *
     * @param id the ID of the booking to cancel
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> cancelBooking(@PathVariable("id") int id) {
        bookingService.cancelBooking(id);
        return new ResponseEntity<>("Booking cancelled successfully.", HttpStatus.OK);
    }

    /**
     * Retrieves all bookings.
     *
     * @return a list of all booking data transfer objects
     */
    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        List<BookingDTO> bookingDTOs = bookings.stream().map(bookingMapper::toDto).collect(Collectors.toList());
        return new ResponseEntity<>(bookingDTOs, HttpStatus.OK);
    }

    /**
     * Filters bookings based on the provided criteria.
     *
     * @param date       the date to filter by
     * @param username   the username to filter by
     * @param resourceId the resource ID to filter by
     * @return a list of filtered booking data transfer objects
     */
    @GetMapping("/filter")
    public ResponseEntity<List<BookingDTO>> filterBookings(
            @RequestParam(value = "date", required = false) LocalDateTime date,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "resourceId", required = false) Integer resourceId) {
        List<Booking> bookings = bookingService.filterBookings(date, username, resourceId);
        List<BookingDTO> bookingDTOs = bookings.stream().map(bookingMapper::toDto).collect(Collectors.toList());
        return new ResponseEntity<>(bookingDTOs, HttpStatus.OK);
    }
}
