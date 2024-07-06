package com.ylab.mapper;

import com.ylab.dto.ConferenceRoomDTO;
import com.ylab.model.ConferenceRoom;
import org.mapstruct.Mapper;

@Mapper
public interface ConferenceRoomMapper {
    ConferenceRoomDTO toDto(ConferenceRoom conferenceRoom);
    ConferenceRoom toEntity(ConferenceRoomDTO conferenceRoomDTO);
}
