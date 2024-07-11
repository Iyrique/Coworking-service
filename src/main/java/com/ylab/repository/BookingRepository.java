package com.ylab.repository;

import com.ylab.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository {

    void save(Booking booking);

    void cancel(int id);

    List<Booking> findAll();

    List<Booking> filter(LocalDateTime date, String username, Integer resourceId);
}
