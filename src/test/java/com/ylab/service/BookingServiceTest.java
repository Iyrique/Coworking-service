package com.ylab.service;

import com.ylab.model.Booking;

import com.ylab.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Service class for booking operations.
 */
class BookingServiceTest {
    private BookingRepository bookingRepository;
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        bookingRepository = Mockito.mock(BookingRepository.class);
        bookingService = new BookingService(bookingRepository);
    }

    @Test
    @DisplayName("Book a resource")
    void bookResource() {
        Booking booking = new Booking(null, "testUser", 1, LocalDateTime.now(), LocalDateTime.now().plusHours(1), true);
        bookingService.bookResource(booking);
        verify(bookingRepository, times(1)).save(booking);
    }

    @Test
    @DisplayName("Cancel a booking")
    void cancelBooking() {
        bookingService.cancelBooking(1);
        verify(bookingRepository, times(1)).cancel(1);
    }

    @Test
    @DisplayName("Retrieve all bookings")
    void getAllBookings() {
        List<Booking> mockBookings = new ArrayList<>();
        mockBookings.add(new Booking(1L, "testUser", 1, LocalDateTime.now(), LocalDateTime.now().plusHours(1), true));
        when(bookingRepository.findAll()).thenReturn(mockBookings);

        List<Booking> bookings = bookingService.getAllBookings();
        assertEquals(1, bookings.size());
        assertEquals("testUser", bookings.get(0).getUsername());
    }

    @Test
    @DisplayName("Filter bookings by date")
    void filterBookingsByDate() {
        LocalDateTime date = LocalDateTime.of(2023, 6, 25, 0, 0);
        List<Booking> mockBookings = new ArrayList<>();
        mockBookings.add(new Booking(1L, "testUser", 1, date, date.plusHours(1), true));
        when(bookingRepository.filter(date, null, null)).thenReturn(mockBookings);

        List<Booking> bookings = bookingService.filterBookings(date, null, null);
        assertEquals(1, bookings.size());
        assertEquals("testUser", bookings.get(0).getUsername());
    }

    @Test
    @DisplayName("Filter bookings by username")
    void filterBookingsByUsername() {
        String username = "testUser";
        List<Booking> mockBookings = new ArrayList<>();
        mockBookings.add(new Booking(1L, username, 1, LocalDateTime.now(), LocalDateTime.now().plusHours(1), true));
        when(bookingRepository.filter(null, username, null)).thenReturn(mockBookings);

        List<Booking> bookings = bookingService.filterBookings(null, username, null);
        assertEquals(1, bookings.size());
        assertEquals(username, bookings.get(0).getUsername());
    }

    @Test
    @DisplayName("Filter bookings by resource ID")
    void filterBookingsByResourceId() {
        int resourceId = 1;
        List<Booking> mockBookings = new ArrayList<>();
        mockBookings.add(new Booking(1L, "testUser", resourceId, LocalDateTime.now(), LocalDateTime.now().plusHours(1), true));
        when(bookingRepository.filter(null, null, resourceId)).thenReturn(mockBookings);

        List<Booking> bookings = bookingService.filterBookings(null, null, resourceId);
        assertEquals(1, bookings.size());
        assertEquals(resourceId, bookings.get(0).getResourceId());
    }

    @Test
    @DisplayName("Filter bookings by date, username, and resource ID")
    void filterBookingsByAllCriteria() {
        LocalDateTime date = LocalDateTime.of(2023, 6, 25, 0, 0);
        String username = "testUser";
        int resourceId = 1;
        List<Booking> mockBookings = new ArrayList<>();
        mockBookings.add(new Booking(1L, username, resourceId, date, date.plusHours(1), true));
        when(bookingRepository.filter(date, username, resourceId)).thenReturn(mockBookings);

        List<Booking> bookings = bookingService.filterBookings(date, username, resourceId);
        assertEquals(1, bookings.size());
        assertEquals(username, bookings.get(0).getUsername());
        assertEquals(resourceId, bookings.get(0).getResourceId());
    }
}
