package com.ylab.mapper;

import com.ylab.dto.BookingDTO;
import com.ylab.model.Booking;
import org.mapstruct.Mapper;

import java.util.List;

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

    /**
     * Converts a list of Booking entities to a list of BookingDTOs.
     *
     * @param bookings the list of Booking entities to convert
     * @return the converted list of BookingDTOs
     */
    List<BookingDTO> toDtoList(List<Booking> bookings);

    /**
     * Converts a list of BookingDTOs to a list of Booking entities.
     *
     * @param bookingDTOs the list of BookingDTOs to convert
     * @return the converted list of Booking entities
     */
    List<Booking> toEntityList(List<BookingDTO> bookingDTOs);

}
