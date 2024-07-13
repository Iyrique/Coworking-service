package com.ylab.service.impl;

import com.ylab.model.Booking;
import com.ylab.repository.BookingRepository;
import com.ylab.repository.impl.BookingRepositoryImpl;
import com.ylab.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for booking operations.
 */
@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepositoryImpl) {
        this.bookingRepository = bookingRepositoryImpl;
    }

    /**
     * Books a workspace or conference room.
     *
     * @param booking the booking details
     */
    public void bookResource(Booking booking) {
        bookingRepository.save(booking);
    }

    /**
     * Cancels a booking by ID.
     *
     * @param bookingId the ID of the booking to cancel
     */
    public void cancelBooking(int bookingId) {
        bookingRepository.cancel(bookingId);
    }

    /**
     * Retrieves all bookings.
     *
     * @return a list of all bookings
     */
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    /**
     * Filters bookings based on the provided criteria.
     *
     * @param date       the date to filter by
     * @param username   the username to filter by
     * @param resourceId the resource ID to filter by
     * @return a list of filtered bookings
     */
    public List<Booking> filterBookings(LocalDateTime date, String username, Integer resourceId) {
        return bookingRepository.filter(date, username, resourceId);
    }
}
