package com.ylab.mapper;

import com.ylab.dto.WorkspaceDTO;
import com.ylab.model.Workspace;
import org.mapstruct.Mapper;

@Mapper
public interface WorkspaceMapper {
    WorkspaceDTO toDto(Workspace workspace);
    Workspace toEntity(WorkspaceDTO workspaceDTO);
}
