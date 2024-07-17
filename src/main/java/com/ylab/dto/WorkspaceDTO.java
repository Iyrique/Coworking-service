package com.ylab.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "Details about the workspace")
public class WorkspaceDTO {

    @ApiModelProperty(notes = "The unique ID of the workspace")
    private Long id;

    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    @ApiModelProperty(notes = "The name of the workspace")
    private String name;

    @ApiModelProperty(notes = "Availability status of the workspace")
    private boolean isAvailable;
}
