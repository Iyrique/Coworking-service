package com.ylab.mapper;

import com.ylab.dto.WorkspaceDTO;
import com.ylab.model.Workspace;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting Workspace entities to WorkspaceDTOs and vice versa.
 */
@Mapper(componentModel = "spring")
public interface WorkspaceMapper {

    /**
     * Converts a Workspace entity to a WorkspaceDTO.
     *
     * @param workspace the Workspace entity to convert
     * @return the converted WorkspaceDTO
     */
    WorkspaceDTO toDto(Workspace workspace);

    /**
     * Converts a WorkspaceDTO to a Workspace entity.
     *
     * @param workspaceDTO the WorkspaceDTO to convert
     * @return the converted Workspace entity
     */
    Workspace toEntity(WorkspaceDTO workspaceDTO);
}
