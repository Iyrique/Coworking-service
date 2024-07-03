package com.ylab.service;

import com.ylab.model.ConferenceRoom;
import com.ylab.model.Workspace;
import com.ylab.repository.ResourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ResourceServiceTest {

    private ResourceRepository resourceRepository;
    private ResourceService resourceService;

    @BeforeEach
    void setUp() {
        resourceRepository = Mockito.mock(ResourceRepository.class);
        resourceService = new ResourceService(resourceRepository);
    }

    @Test
    @DisplayName("Get all workspaces")
    void getAllWorkspaces() {
        Map<Long, Workspace> mockWorkspaces = new HashMap<>();
        mockWorkspaces.put(1L, new Workspace(1L, "Workspace 1", true));
        mockWorkspaces.put(2L, new Workspace(2L, "Workspace 2", false));
        when(resourceRepository.getAllWorkspaces()).thenReturn(mockWorkspaces);

        Map<Long, Workspace> workspaces = resourceService.getAllWorkspaces();
        assertEquals(2, workspaces.size());
        assertTrue(workspaces.containsKey(1L));
        assertTrue(workspaces.containsKey(2L));
    }

    @Test
    @DisplayName("Get all conference rooms")
    void getAllConferenceRooms() {
        Map<Long, ConferenceRoom> mockRooms = new HashMap<>();
        mockRooms.put(1L, new ConferenceRoom(1L, "Conference Room 1", true));
        mockRooms.put(2L, new ConferenceRoom(2L, "Conference Room 2", false));
        when(resourceRepository.getAllConferenceRooms()).thenReturn(mockRooms);

        Map<Long, ConferenceRoom> rooms = resourceService.getAllConferenceRooms();
        assertEquals(2, rooms.size());
        assertTrue(rooms.containsKey(1L));
        assertTrue(rooms.containsKey(2L));
    }

    @Test
    @DisplayName("Add new workspace")
    void addWorkspace() {
        Workspace workspace = new Workspace(null, "Workspace 1", true);
        resourceService.addWorkspace(workspace);
        verify(resourceRepository, times(1)).saveWorkspace(workspace);
    }

    @Test
    @DisplayName("Add new conference room")
    void addConferenceRoom() {
        ConferenceRoom room = new ConferenceRoom(null, "Conference Room 1", true);
        resourceService.addConferenceRoom(room);
        verify(resourceRepository, times(1)).saveConferenceRoom(room);
    }

    @Test
    @DisplayName("Update workspace")
    void updateWorkspace() {
        resourceService.updateWorkspace(1, "Updated Workspace", false);
        verify(resourceRepository, times(1)).updateWorkspace(1, "Updated Workspace", false);
    }

    @Test
    @DisplayName("Update conference room")
    void updateConferenceRoom() {
        resourceService.updateConferenceRoom(1, "Updated Conference Room", false);
        verify(resourceRepository, times(1)).updateConferenceRoom(1, "Updated Conference Room", false);
    }

    @Test
    @DisplayName("Delete workspace")
    void deleteWorkspace() {
        resourceService.deleteWorkspace(1);
        verify(resourceRepository, times(1)).deleteWorkspace(1);
    }

    @Test
    @DisplayName("Delete conference room")
    void deleteConferenceRoom() {
        resourceService.deleteConferenceRoom(1);
        verify(resourceRepository, times(1)).deleteConferenceRoom(1);
    }

    @Test
    @DisplayName("Get workspace by ID")
    void getWorkspaceById() {
        Workspace mockWorkspace = new Workspace(1L, "Workspace 1", true);
        Map<Long, Workspace> mockWorkspaces = new HashMap<>();
        mockWorkspaces.put(1L, mockWorkspace);
        when(resourceRepository.getAllWorkspaces()).thenReturn(mockWorkspaces);

        Workspace workspace = resourceService.getWorkspaceById(1L);
        assertNotNull(workspace);
        assertEquals("Workspace 1", workspace.getName());
    }

    @Test
    @DisplayName("Get conference room by ID")
    void getConferenceRoomById() {
        ConferenceRoom mockRoom = new ConferenceRoom(1L, "Conference Room 1", true);
        Map<Long, ConferenceRoom> mockRooms = new HashMap<>();
        mockRooms.put(1L, mockRoom);
        when(resourceRepository.getAllConferenceRooms()).thenReturn(mockRooms);

        ConferenceRoom room = resourceService.getConferenceRoomById(1L);
        assertNotNull(room);
        assertEquals("Conference Room 1", room.getName());
    }
}
