package com.ylab.controller;

import com.ylab.dto.BookingDTO;
import com.ylab.mapper.BookingMapper;
import com.ylab.model.Booking;
import com.ylab.service.BookingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@Tag(name = "Booking Management System", description = "Operations pertaining to bookings in Booking Management System")
public class BookingController {

    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    /**
     * Books a workspace or conference room.
     *
     * @param bookingDTO the booking data transfer object
     */
    @PostMapping
    @Operation(summary = "Book a resource", description = "Books a workspace or conference room")
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
    @Operation(summary = "Cancel a booking by ID", description = "Cancels a booking by ID")
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
    @Operation(summary = "View a list of all bookings", description = "Retrieves all bookings")
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        List<BookingDTO> bookingDTOs = bookingMapper.toDtoList(bookings);
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
    @Operation(summary = "Filter bookings based on criteria", description = "Filters bookings based on the provided criteria")
    public ResponseEntity<List<BookingDTO>> filterBookings(
            @RequestParam(value = "date", required = false) LocalDateTime date,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "resourceId", required = false) Integer resourceId) {
        List<Booking> bookings = bookingService.filterBookings(date, username, resourceId);
        List<BookingDTO> bookingDTOs = bookingMapper.toDtoList(bookings);
        return new ResponseEntity<>(bookingDTOs, HttpStatus.OK);
    }
}
