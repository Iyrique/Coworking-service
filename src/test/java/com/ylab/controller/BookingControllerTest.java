package com.ylab.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ylab.dto.BookingDTO;
import com.ylab.mapper.BookingMapper;
import com.ylab.model.Booking;
import com.ylab.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;


public class BookingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookingService bookingService;

    @Mock
    private BookingMapper bookingMapper;

    @InjectMocks
    private BookingController bookingController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("Test booking resource")
    public void testBookResource() throws Exception {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setUsername("testuser");
        bookingDTO.setStartTime(LocalDateTime.now());
        bookingDTO.setEndTime(LocalDateTime.now());
        bookingDTO.setResourceId(1);

        Booking booking = new Booking();

        when(bookingMapper.toEntity(any(BookingDTO.class))).thenReturn(booking);

        String bookingJson = objectMapper.writeValueAsString(bookingDTO);

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookingJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("Booking created successfully."));

        verify(bookingService, times(1)).bookResource(any(Booking.class));
    }

    @Test
    @DisplayName("Test cancel booking")
    public void testCancelBooking() throws Exception {
        int bookingId = 1;

        mockMvc.perform(delete("/api/bookings/{id}", bookingId))
                .andExpect(status().isOk())
                .andExpect(content().string("Booking cancelled successfully."));


    }

    @Test
    @DisplayName("Test get all bookings")
    public void testGetAllBookings() throws Exception {
        Booking booking = new Booking();
        BookingDTO bookingDTO = new BookingDTO();

        when(bookingService.getAllBookings()).thenReturn(Collections.singletonList(booking));
        when(bookingMapper.toDto(any(Booking.class))).thenReturn(bookingDTO);

        mockMvc.perform(get("/api/bookings"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}]"));

        verify(bookingService, times(1)).getAllBookings();
    }

    @Test
    @DisplayName("Test filter bookings")
    public void testFilterBookings() throws Exception {
        Booking booking = new Booking();
        BookingDTO bookingDTO = new BookingDTO();

        when(bookingService.filterBookings(any(LocalDateTime.class), anyString(), anyInt())).thenReturn(Collections.singletonList(booking));
        when(bookingMapper.toDto(any(Booking.class))).thenReturn(bookingDTO);

        mockMvc.perform(get("/api/bookings/filter")
                        .param("date", "2024-07-13T00:00:00")
                        .param("username", "testuser")
                        .param("resourceId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}]"));

        verify(bookingService, times(1)).filterBookings(any(LocalDateTime.class), anyString(), anyInt());
    }
}
