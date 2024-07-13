package com.ylab.mapper;

import com.ylab.dto.BookingDTO;
import com.ylab.model.Booking;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting Booking entities to BookingDTOs and vice versa.
 */

@Mapper(componentModel = "spring")
public interface BookingMapper {

    /**
     * Converts a Booking entity to a BookingDTO.
     *
     * @param booking the Booking entity to convert
     * @return the converted BookingDTO
     */
    BookingDTO toDto(Booking booking);

    /**
     * Converts a BookingDTO to a Booking entity.
     *
     * @param bookingDTO the BookingDTO to convert
     * @return the converted Booking entity
     */
    Booking toEntity(BookingDTO bookingDTO);
}
