package com.ylab.service;

import com.ylab.model.ConferenceRoom;
import com.ylab.model.Workspace;

import java.util.Map;

public interface ResourceService {
    Map<Long, Workspace> getAllWorkspaces();

    Map<Long, ConferenceRoom> getAllConferenceRooms();

    void addWorkspace(Workspace workspace);

    void addConferenceRoom(ConferenceRoom room);

    void updateWorkspace(int id, String newName, boolean isAvailable);

    void updateConferenceRoom(int id, String newName, boolean isAvailable);

    void deleteWorkspace(int id);

    void deleteConferenceRoom(int id);

    Workspace getWorkspaceById(long id);

    ConferenceRoom getConferenceRoomById(long id);

}
