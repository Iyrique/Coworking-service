package com.ylab.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylab.dto.ConferenceRoomDTO;
import com.ylab.dto.WorkspaceDTO;
import com.ylab.mapper.ConferenceRoomMapper;
import com.ylab.mapper.WorkspaceMapper;
import com.ylab.model.ConferenceRoom;
import com.ylab.model.Workspace;
import com.ylab.service.ResourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

public class ResourceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ResourceService resourceService;

    @Mock
    private WorkspaceMapper workspaceMapper;

    @Mock
    private ConferenceRoomMapper conferenceRoomMapper;

    @InjectMocks
    private ResourceController resourceController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(resourceController).build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @Test
    @DisplayName("Test get all workspaces")
    public void testGetAllWorkspaces() throws Exception {
        Workspace workspace = new Workspace();
        WorkspaceDTO workspaceDTO = new WorkspaceDTO();

        when(resourceService.getAllWorkspaces()).thenReturn(Collections.singletonMap(1L, workspace));
        when(workspaceMapper.toDto(any(Workspace.class))).thenReturn(workspaceDTO);

        mockMvc.perform(get("/api/resources/workspaces"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}]"));

        verify(resourceService, times(1)).getAllWorkspaces();
    }

    @Test
    @DisplayName("Test get all conference rooms")
    public void testGetAllConferenceRooms() throws Exception {
        ConferenceRoom conferenceRoom = new ConferenceRoom();
        ConferenceRoomDTO conferenceRoomDTO = new ConferenceRoomDTO();

        when(resourceService.getAllConferenceRooms()).thenReturn(Collections.singletonMap(1L, conferenceRoom));
        when(conferenceRoomMapper.toDto(any(ConferenceRoom.class))).thenReturn(conferenceRoomDTO);

        mockMvc.perform(get("/api/resources/conferenceRooms"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}]"));

        verify(resourceService, times(1)).getAllConferenceRooms();
    }

    @Test
    @DisplayName("Test add workspace")
    public void testAddWorkspace() throws Exception {
        WorkspaceDTO workspaceDTO = new WorkspaceDTO();
        Workspace workspace = new Workspace();

        when(workspaceMapper.toEntity(any(WorkspaceDTO.class))).thenReturn(workspace);

        String workspaceJson = objectMapper.writeValueAsString(workspaceDTO);

        mockMvc.perform(post("/api/resources/workspace")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(workspaceJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("Workspace added successfully."));

        verify(resourceService, times(1)).addWorkspace(any(Workspace.class));
    }

    @Test
    @DisplayName("Test add conference room")
    public void testAddConferenceRoom() throws Exception {
        ConferenceRoomDTO conferenceRoomDTO = new ConferenceRoomDTO();
        ConferenceRoom conferenceRoom = new ConferenceRoom();

        when(conferenceRoomMapper.toEntity(any(ConferenceRoomDTO.class))).thenReturn(conferenceRoom);

        String conferenceRoomJson = objectMapper.writeValueAsString(conferenceRoomDTO);

        mockMvc.perform(post("/api/resources/conferenceRoom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(conferenceRoomJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("Conference room added successfully."));

        verify(resourceService, times(1)).addConferenceRoom(any(ConferenceRoom.class));
    }


    @Test
    @DisplayName("Test delete workspace")
    public void testDeleteWorkspace() throws Exception {
        mockMvc.perform(delete("/api/resources/workspace/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("Workspace deleted successfully."));

        verify(resourceService, times(1)).deleteWorkspace(anyInt());
    }

    @Test
    @DisplayName("Test delete conference room")
    public void testDeleteConferenceRoom() throws Exception {
        mockMvc.perform(delete("/api/resources/conferenceRoom/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string("Conference room deleted successfully."));

        verify(resourceService, times(1)).deleteConferenceRoom(anyInt());
    }

    @Test
    @DisplayName("Test get workspace by ID")
    public void testGetWorkspaceById() throws Exception {
        Workspace workspace = new Workspace();
        WorkspaceDTO workspaceDTO = new WorkspaceDTO();

        when(resourceService.getWorkspaceById(1L)).thenReturn(workspace);
        when(workspaceMapper.toDto(any(Workspace.class))).thenReturn(workspaceDTO);

        mockMvc.perform(get("/api/resources/workspace/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

        verify(resourceService, times(1)).getWorkspaceById(1L);
    }

    @Test
    @DisplayName("Test get workspace by ID not found")
    public void testGetWorkspaceByIdNotFound() throws Exception {
        when(resourceService.getWorkspaceById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/resources/workspace/{id}", 1))
                .andExpect(status().isNotFound());

        verify(resourceService, times(1)).getWorkspaceById(1L);
    }

    @Test
    @DisplayName("Test get conference room by ID")
    public void testGetConferenceRoomById() throws Exception {
        ConferenceRoom conferenceRoom = new ConferenceRoom();
        ConferenceRoomDTO conferenceRoomDTO = new ConferenceRoomDTO();

        when(resourceService.getConferenceRoomById(1L)).thenReturn(conferenceRoom);
        when(conferenceRoomMapper.toDto(any(ConferenceRoom.class))).thenReturn(conferenceRoomDTO);

        mockMvc.perform(get("/api/resources/conferenceRoom/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));

        verify(resourceService, times(1)).getConferenceRoomById(1L);
    }

    @Test
    @DisplayName("Test get conference room by ID not found")
    public void testGetConferenceRoomByIdNotFound() throws Exception {
        when(resourceService.getConferenceRoomById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/resources/conferenceRoom/{id}", 1))
                .andExpect(status().isNotFound());

        verify(resourceService, times(1)).getConferenceRoomById(1L);
    }
}
