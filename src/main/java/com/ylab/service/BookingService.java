package com.ylab.service;

import com.ylab.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {

    void bookResource(Booking booking);

    void cancelBooking(int bookingId);

    List<Booking> getAllBookings();

    List<Booking> filterBookings(LocalDateTime date, String username, Integer resourceId);
}
