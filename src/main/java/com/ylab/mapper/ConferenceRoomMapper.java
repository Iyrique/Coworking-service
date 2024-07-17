package com.ylab.mapper;

import com.ylab.dto.ConferenceRoomDTO;
import com.ylab.model.ConferenceRoom;
import org.mapstruct.Mapper;

import java.util.List;


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

    /**
     * Converts a list of ConferenceRoom entities to a list of ConferenceRoomDTOs.
     *
     * @param conferenceRoom the list of ConferenceRoom entities to convert
     * @return the converted list of ConferenceRoomDTOs
     */
    List<ConferenceRoomDTO> toDtoList(List<ConferenceRoom> conferenceRoom);

    /**
     * Converts a list of ConferenceRoomDTOs to a list of ConferenceRoom entities.
     *
     * @param conferenceRoomDTOs the list of ConferenceRoomDTOs to convert
     * @return the converted list of ConferenceRoom entities
     */
    List<ConferenceRoom> toEntityList(List<ConferenceRoomDTO> conferenceRoomDTOs);
}
