package com.ylab.mapper;

import com.ylab.dto.WorkspaceDTO;
import com.ylab.model.Workspace;
import org.mapstruct.Mapper;

import java.util.List;

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

    /**
     * Converts a list of Workspace entities to a list of WorkspaceDTOs.
     *
     * @param workspaces the list of Workspace entities to convert
     * @return the converted list of WorkspaceDTOs
     */
    List<WorkspaceDTO> toDtoList(List<Workspace> workspaces);

    /**
     * Converts a list of WorkspaceDTOs to a list of Workspace entities.
     *
     * @param workspaceDTOSDTOs the list of WorkspaceDTOs to convert
     * @return the converted list of Workspace entities
     */
    List<Workspace> toEntityList(List<WorkspaceDTO> workspaceDTOSDTOs);
}
