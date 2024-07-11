package com.ylab.repository.impl;

import com.ylab.connector.ConnectorDB;
import com.ylab.model.ConferenceRoom;
import com.ylab.model.Workspace;
import com.ylab.repository.ConferenceRoomRepository;
import com.ylab.repository.ResourceRepository;
import com.ylab.repository.WorkspaceRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Repository class for Workspace and ConferenceRoom entities.
 */
public class ResourceRepositoryImpl implements ResourceRepository {

    private final ConnectorDB connectorDB = ConnectorDB.getInstance();

    /**
     * Retrieves all workspaces from the database.
     *
     * @return a map of workspace ID to Workspace objects
     */
    public Map<Long, Workspace> getAllWorkspaces() {
        Map<Long, Workspace> workspaces = new HashMap<>();
        try {
            String sql = "SELECT * FROM coworking.workspaces";
            PreparedStatement statement = connectorDB.getConnection().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                workspaces.put(resultSet.getLong("id"), new Workspace(resultSet.getLong("id"), resultSet.getString("name"),
                        resultSet.getBoolean("is_available")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return workspaces;
    }

    /**
     * Retrieves all conference rooms from the database.
     *
     * @return a map of conference room ID to ConferenceRoom objects
     */
    public Map<Long, ConferenceRoom> getAllConferenceRooms() {
        Map<Long, ConferenceRoom> rooms = new HashMap<>();
        try {
            String sql = "SELECT * FROM coworking.conference_rooms";
            PreparedStatement statement = connectorDB.getConnection().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                rooms.put(resultSet.getLong("id"), new ConferenceRoom(resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getBoolean("is_available")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    /**
     * Saves a workspace to the database.
     *
     * @param workspace the workspace to save
     */
    public void saveWorkspace(Workspace workspace) {
        try {
            String sql = "INSERT INTO coworking.workspaces (name, is_available) VALUES (?, ?)";
            PreparedStatement statement = prepareInsertStatement(connectorDB.getConnection(), sql, workspace.getName(),
                    workspace.isAvailable());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves a conference room to the database.
     *
     * @param room the conference room to save
     */
    public void saveConferenceRoom(ConferenceRoom room) {
        try {
            String sql = "INSERT INTO coworking.conference_rooms (name, is_available) VALUES (?, ?)";
            PreparedStatement statement = prepareInsertStatement(connectorDB.getConnection(), sql, room.getName(), room.isAvailable());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private PreparedStatement prepareInsertStatement(Connection connection, String sql, String name,
                                                     boolean isAvailable) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        statement.setBoolean(2, isAvailable);
        return statement;
    }

    /**
     * Updates a workspace in the database.
     *
     * @param id          the ID of the workspace to update
     * @param newName     the new name of the workspace
     * @param isAvailable the new availability status of the workspace
     */
    public void updateWorkspace(int id, String newName, boolean isAvailable) {
        try {
            String sql = "UPDATE coworking.workspaces SET name = ?, is_available = ? WHERE id = ?";
            PreparedStatement statement = prepareUpdateStatement(connectorDB.getConnection(), sql, id, newName, isAvailable);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates a conference room in the database.
     *
     * @param id          the ID of the conference room to update
     * @param newName     the new name of the conference room
     * @param isAvailable the new availability status of the conference room
     */
    public void updateConferenceRoom(int id, String newName, boolean isAvailable) {
        try {
            String sql = "UPDATE coworking.conference_rooms SET name = ?, is_available = ? WHERE id = ?";
            PreparedStatement statement = prepareUpdateStatement(connectorDB.getConnection(), sql, id, newName, isAvailable);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private PreparedStatement prepareUpdateStatement(Connection connection, String sql, int id, String newName,
                                                     boolean isAvailable) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, newName);
        statement.setBoolean(2, isAvailable);
        statement.setInt(3, id);
        return statement;
    }

    /**
     * Deletes a workspace from the database.
     *
     * @param id the ID of the workspace to delete
     */
    public void deleteWorkspace(int id) {
        try {
            String sql = "DELETE FROM coworking.workspaces WHERE id = ?";
            PreparedStatement statement = prepareDeleteStatement(connectorDB.getConnection(), sql, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a conference room from the database.
     *
     * @param id the ID of the conference room to delete
     */
    public void deleteConferenceRoom(int id) {
        try {
            String sql = "DELETE FROM coworking.conference_rooms WHERE id = ?";
            PreparedStatement statement = prepareDeleteStatement(connectorDB.getConnection(), sql, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private PreparedStatement prepareDeleteStatement(Connection connection, String sql, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        return statement;
    }
}
