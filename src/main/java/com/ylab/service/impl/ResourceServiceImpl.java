package com.ylab.service.impl;

import com.ylab.model.ConferenceRoom;
import com.ylab.model.Workspace;
import com.ylab.repository.ResourceRepository;
import com.ylab.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;

    /**
     * Retrieves all available workspaces.
     *
     * @return a map of workspace ID to Workspace objects
     */
    public Map<Long, Workspace> getAllWorkspaces() {
        return resourceRepository.getAllWorkspaces();
    }

    /**
     * Retrieves all available conference rooms.
     *
     * @return a map of conference room ID to ConferenceRoom objects
     */
    public Map<Long, ConferenceRoom> getAllConferenceRooms() {
        return resourceRepository.getAllConferenceRooms();
    }

    /**
     * Adds a new workspace.
     *
     * @param workspace the workspace to add
     */
    public void addWorkspace(Workspace workspace) {
        resourceRepository.saveWorkspace(workspace);
    }

    /**
     * Adds a new conference room.
     *
     * @param room the conference room to add
     */
    public void addConferenceRoom(ConferenceRoom room) {
        resourceRepository.saveConferenceRoom(room);
    }

    /**
     * Updates a workspace.
     *
     * @param id          the ID of the workspace to update
     * @param newName     the new name of the workspace
     * @param isAvailable the new availability status of the workspace
     */
    public void updateWorkspace(int id, String newName, boolean isAvailable) {
        resourceRepository.updateWorkspace(id, newName, isAvailable);
    }

    /**
     * Updates a conference room.
     *
     * @param id          the ID of the conference room to update
     * @param newName     the new name of the conference room
     * @param isAvailable the new availability status of the conference room
     */
    public void updateConferenceRoom(int id, String newName, boolean isAvailable) {
        resourceRepository.updateConferenceRoom(id, newName, isAvailable);
    }

    /**
     * Deletes an existing workspace.
     *
     * @param id the ID of the workspace
     */
    public void deleteWorkspace(int id) {
        resourceRepository.deleteWorkspace(id);
    }

    /**
     * Deletes an existing conference room.
     *
     * @param id the ID of the conference room
     */
    public void deleteConferenceRoom(int id) {
        resourceRepository.deleteConferenceRoom(id);
    }

    /**
     * Gets a specific workspace by its ID.
     *
     * @param id the ID of the workspace to retrieve
     * @return the workspace if found, null otherwise
     */
    public Workspace getWorkspaceById(long id) {
        return resourceRepository.getAllWorkspaces().get(id);
    }

    /**
     * Gets a specific conference room by its ID.
     *
     * @param id the ID of the conference room to retrieve
     * @return the conference room if found, null otherwise
     */
    public ConferenceRoom getConferenceRoomById(long id) {
        return resourceRepository.getAllConferenceRooms().get(id);
    }

}
