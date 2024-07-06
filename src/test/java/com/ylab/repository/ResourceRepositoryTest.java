package com.ylab.repository;

import com.ylab.connector.ConnectorDB;
import com.ylab.model.ConferenceRoom;
import com.ylab.model.Workspace;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ResourceRepository.
 */
@Testcontainers
public class ResourceRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    private static ResourceRepository resourceRepository;

    @BeforeAll
    public static void setUp() throws SQLException {
        ConnectorDB.setConnectionDetails(
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword()
        );
        try (Connection connection = DriverManager.getConnection(
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword()
        )) {
            connection.createStatement().execute("CREATE SCHEMA coworking");
            connection.createStatement().execute(
                    "CREATE TABLE coworking.workspaces (" +
                            "id SERIAL PRIMARY KEY, " +
                            "name VARCHAR(50) NOT NULL, " +
                            "is_available BOOLEAN NOT NULL)"
            );
            connection.createStatement().execute(
                    "CREATE TABLE coworking.conference_rooms (" +
                            "id SERIAL PRIMARY KEY, " +
                            "name VARCHAR(50) NOT NULL, " +
                            "is_available BOOLEAN NOT NULL)"
            );
        }

        resourceRepository = new ResourceRepository();
    }

    @AfterEach
    public void clearDatabase() throws SQLException {
        try (Connection connection = DriverManager.getConnection(
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword()
        )) {
            connection.createStatement().execute("TRUNCATE TABLE coworking.workspaces RESTART IDENTITY CASCADE");
            connection.createStatement().execute("TRUNCATE TABLE coworking.conference_rooms RESTART IDENTITY CASCADE");
        }
    }


//    @Test
//    @DisplayName("Test saving and retrieving all workspaces")
//    public void testSaveAndGetAllWorkspaces() {
//        Workspace workspace = new Workspace(null, "Workspace 1", true);
//        resourceRepository.saveWorkspace(workspace);
//
//        Map<Long, Workspace> workspaces = resourceRepository.getAllWorkspaces();
//        assertNotNull(workspaces);
//        assertFalse(workspaces.isEmpty());
//        assertEquals("Workspace 1", workspaces.values().iterator().next().getName());
//    }
//
//    @Test
//    @DisplayName("Test saving and retrieving all conference rooms")
//    public void testSaveAndGetAllConferenceRooms() {
//        ConferenceRoom room = new ConferenceRoom(null, "Conference Room 1", true);
//        resourceRepository.saveConferenceRoom(room);
//
//        Map<Long, ConferenceRoom> rooms = resourceRepository.getAllConferenceRooms();
//        assertNotNull(rooms);
//        assertFalse(rooms.isEmpty());
//        assertEquals("Conference Room 1", rooms.values().iterator().next().getName());
//    }
//
//    @Test
//    @DisplayName("Test updating a workspace")
//    public void testUpdateWorkspace() {
//        Workspace workspace = new Workspace(null, "Workspace 2", true);
//        resourceRepository.saveWorkspace(workspace);
//
//        Map<Long, Workspace> workspaces = resourceRepository.getAllWorkspaces();
//        Long id = workspaces.keySet().iterator().next();
//        resourceRepository.updateWorkspace(id.intValue(), "Updated Workspace", false);
//
//        workspaces = resourceRepository.getAllWorkspaces();
//        Workspace updatedWorkspace = workspaces.get(id);
//        assertEquals("Updated Workspace", updatedWorkspace.getName());
//        assertFalse(updatedWorkspace.isAvailable());
//    }
//
//    @Test
//    @DisplayName("Test updating a conference room")
//    public void testUpdateConferenceRoom() {
//        ConferenceRoom room = new ConferenceRoom(null, "Conference Room 2", true);
//        resourceRepository.saveConferenceRoom(room);
//
//        Map<Long, ConferenceRoom> rooms = resourceRepository.getAllConferenceRooms();
//        Long id = rooms.keySet().iterator().next();
//        resourceRepository.updateConferenceRoom(id.intValue(), "Updated Conference Room", false);
//
//        rooms = resourceRepository.getAllConferenceRooms();
//        ConferenceRoom updatedRoom = rooms.get(id);
//        assertEquals("Updated Conference Room", updatedRoom.getName());
//        assertFalse(updatedRoom.isAvailable());
//    }

//    @Test
//    @DisplayName("Test deleting a workspace")
//    public void testDeleteWorkspace() {
//        Workspace workspace = new Workspace(null, "Workspace 3", true);
//        resourceRepository.saveWorkspace(workspace);
//
//        Map<Long, Workspace> workspaces = resourceRepository.getAllWorkspaces();
//        Long id = workspaces.keySet().iterator().next();
//        resourceRepository.deleteWorkspace(id.intValue());
//
//        workspaces = resourceRepository.getAllWorkspaces();
//        assertTrue(workspaces.isEmpty(), "Workspaces should be empty after deletion");
//    }
//
//    @Test
//    @DisplayName("Test deleting a conference room")
//    public void testDeleteConferenceRoom() {
//        ConferenceRoom room = new ConferenceRoom(null, "Conference Room 3", true);
//        resourceRepository.saveConferenceRoom(room);
//
//        Map<Long, ConferenceRoom> rooms = resourceRepository.getAllConferenceRooms();
//        Long id = rooms.keySet().iterator().next();
//        resourceRepository.deleteConferenceRoom(id.intValue());
//
//        rooms = resourceRepository.getAllConferenceRooms();
//        assertTrue(rooms.isEmpty());
//    }
}
