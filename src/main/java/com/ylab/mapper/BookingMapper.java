package com.ylab.mapper;

import com.ylab.dto.BookingDTO;
import com.ylab.model.Booking;
import org.mapstruct.Mapper;

@Mapper
public interface BookingMapper {
    BookingDTO toDto(Booking booking);
    Booking toEntity(BookingDTO bookingDTO);
}
