package com.ylab.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "Details about the workspace")
public class WorkspaceDTO {

    @Schema(description = "The unique ID of the workspace")
    private Long id;

    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    @Schema(description = "The name of the workspace")
    private String name;

    @Schema(description = "Availability status of the workspace")
    private boolean isAvailable;
}
