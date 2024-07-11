package com.ylab.repository;

import com.ylab.model.ConferenceRoom;

import java.util.Map;

public interface ConferenceRoomRepository {

    Map<Long, ConferenceRoom> getAllConferenceRooms();

    void saveConferenceRoom(ConferenceRoom room);

    void updateConferenceRoom(int id, String newName, boolean isAvailable);

    void deleteConferenceRoom(int id);
}
