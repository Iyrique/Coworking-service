package com.ylab.controller;

import com.ylab.dto.ConferenceRoomDTO;
import com.ylab.dto.WorkspaceDTO;
import com.ylab.mapper.ConferenceRoomMapper;
import com.ylab.mapper.WorkspaceMapper;
import com.ylab.model.ConferenceRoom;
import com.ylab.model.Workspace;
import com.ylab.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/resources")
@RequiredArgsConstructor
@Api(value = "Resource Management System", description = "Operations pertaining to resources in Resource Management System")
public class ResourceController {

    private final ResourceService resourceService;
    private final WorkspaceMapper workspaceMapper;
    private final ConferenceRoomMapper conferenceRoomMapper;

    /**
     * Retrieves all available workspaces.
     *
     * @return a list of workspace data transfer objects
     */
    @GetMapping("/workspaces")
    @ApiOperation(value = "View a list of all available workspaces", response = List.class)
    public ResponseEntity<List<WorkspaceDTO>> getAllWorkspaces() {
        List<Workspace> workspaces = resourceService.getAllWorkspaces().values().stream().collect(Collectors.toList());
        List<WorkspaceDTO> workspaceDTOs = workspaceMapper.toDtoList(workspaces);
        return new ResponseEntity<>(workspaceDTOs, HttpStatus.OK);
    }

    /**
     * Retrieves all available conference rooms.
     *
     * @return a list of conference room data transfer objects
     */
    @GetMapping("/conferenceRooms")
    @ApiOperation(value = "View a list of all available conference rooms", response = List.class)
    public ResponseEntity<List<ConferenceRoomDTO>> getAllConferenceRooms() {
        List<ConferenceRoom> conferenceRooms = resourceService.getAllConferenceRooms().values().stream().collect(Collectors.toList());
        List<ConferenceRoomDTO> conferenceRoomDTOs = conferenceRoomMapper.toDtoList(conferenceRooms);
        return new ResponseEntity<>(conferenceRoomDTOs, HttpStatus.OK);
    }

    /**
     * Adds a new workspace.
     *
     * @param workspaceDTO the workspace data transfer object
     */
    @PostMapping("/workspace")
    @ApiOperation(value = "Add a new workspace", response = String.class)
    public ResponseEntity<String> addWorkspace(@RequestBody WorkspaceDTO workspaceDTO) {
        Workspace workspace = workspaceMapper.toEntity(workspaceDTO);
        resourceService.addWorkspace(workspace);
        return new ResponseEntity<>("Workspace added successfully.", HttpStatus.CREATED);
    }

    /**
     * Adds a new conference room.
     *
     * @param conferenceRoomDTO the conference room data transfer object
     */
    @PostMapping("/conferenceRoom")
    @ApiOperation(value = "Add a new conference room", response = String.class)
    public ResponseEntity<String> addConferenceRoom(@RequestBody ConferenceRoomDTO conferenceRoomDTO) {
        ConferenceRoom room = conferenceRoomMapper.toEntity(conferenceRoomDTO);
        resourceService.addConferenceRoom(room);
        return new ResponseEntity<>("Conference room added successfully.", HttpStatus.CREATED);
    }

    /**
     * Updates a workspace.
     *
     * @param id           the ID of the workspace to update
     * @param workspaceDTO the updated workspace data transfer object
     */
    @PutMapping("/workspaces/{id}")
    @ApiOperation(value = "Update a workspace", response = Void.class)
    public void updateWorkspace(@PathVariable("id") int id, @RequestBody WorkspaceDTO workspaceDTO) {
        Workspace workspace = workspaceMapper.toEntity(workspaceDTO);
        resourceService.updateWorkspace(id, workspace.getName(), workspace.isAvailable());
    }

    /**
     * Updates a conference room.
     *
     * @param id                the ID of the conference room to update
     * @param conferenceRoomDTO the updated conference room data transfer object
     */
    @PutMapping("/conference-rooms/{id}")
    @ApiOperation(value = "Update a conference room", response = Void.class)
    public void updateConferenceRoom(@PathVariable("id") int id, @RequestBody ConferenceRoomDTO conferenceRoomDTO) {
        ConferenceRoom conferenceRoom = conferenceRoomMapper.toEntity(conferenceRoomDTO);
        resourceService.updateConferenceRoom(id, conferenceRoom.getName(), conferenceRoom.isAvailable());
    }


    /**
     * Deletes an existing workspace.
     *
     * @param id the ID of the workspace to delete
     */
    @DeleteMapping("/workspace/{id}")
    @ApiOperation(value = "Delete a workspace", response = String.class)
    public ResponseEntity<String> deleteWorkspace(@PathVariable("id") int id) {
        resourceService.deleteWorkspace(id);
        return new ResponseEntity<>("Workspace deleted successfully.", HttpStatus.OK);
    }

    /**
     * Deletes an existing conference room.
     *
     * @param id the ID of the conference room to delete
     */
    @DeleteMapping("/conferenceRoom/{id}")
    @ApiOperation(value = "Delete a conference room", response = String.class)
    public ResponseEntity<String> deleteConferenceRoom(@PathVariable("id") int id) {
        resourceService.deleteConferenceRoom(id);
        return new ResponseEntity<>("Conference room deleted successfully.", HttpStatus.OK);
    }

    /**
     * Get an existing workspace.
     *
     * @param id the ID of the workspace to find
     */
    @GetMapping("/workspace/{id}")
    @ApiOperation(value = "Get a workspace by ID", response = WorkspaceDTO.class)
    public ResponseEntity<WorkspaceDTO> getWorkspaceById(@PathVariable("id") long id) {
        Workspace workspace = resourceService.getWorkspaceById(id);
        return workspace != null ? new ResponseEntity<>(workspaceMapper.toDto(workspace), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Get an existing conference room.
     *
     * @param id the ID of the conference room to find
     */
    @GetMapping("/conferenceRoom/{id}")
    @ApiOperation(value = "Get a conference room by ID", response = ConferenceRoomDTO.class)
    public ResponseEntity<ConferenceRoomDTO> getConferenceRoomById(@PathVariable("id") long id) {
        ConferenceRoom room = resourceService.getConferenceRoomById(id);
        return room != null ? new ResponseEntity<>(conferenceRoomMapper.toDto(room), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
