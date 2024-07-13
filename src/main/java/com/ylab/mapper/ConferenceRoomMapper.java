package com.ylab.mapper;

import com.ylab.dto.ConferenceRoomDTO;
import com.ylab.model.ConferenceRoom;
import org.mapstruct.Mapper;


/**
 * Mapper interface for converting ConferenceRoom entities to ConferenceRoomDTOs and vice versa.
 */
@Mapper(componentModel = "spring")
public interface ConferenceRoomMapper {

    /**
     * Converts a ConferenceRoom entity to a ConferenceRoomDTO.
     *
     * @param conferenceRoom the ConferenceRoom entity to convert
     * @return the converted ConferenceRoomDTO
     */
    ConferenceRoomDTO toDto(ConferenceRoom conferenceRoom);

    /**
     * Converts a ConferenceRoomDTO to a ConferenceRoom entity.
     *
     * @param conferenceRoomDTO the ConferenceRoomDTO to convert
     * @return the converted ConferenceRoom entity
     */
    ConferenceRoom toEntity(ConferenceRoomDTO conferenceRoomDTO);
}
