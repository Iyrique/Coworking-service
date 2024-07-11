package com.ylab.repository;

import com.ylab.model.Workspace;

import java.util.Map;

public interface WorkspaceRepository {

    Map<Long, Workspace> getAllWorkspaces();

    void saveWorkspace(Workspace workspace);

    void updateWorkspace(int id, String newName, boolean isAvailable);

    void deleteWorkspace(int id);
}
