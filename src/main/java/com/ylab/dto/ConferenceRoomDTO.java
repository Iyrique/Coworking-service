package com.ylab.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "Details about the conference room")
public class ConferenceRoomDTO {

    @ApiModelProperty(notes = "The unique ID of the conference room")
    private Long id;

    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    @ApiModelProperty(notes = "The name of the conference room")
    private String name;

    @ApiModelProperty(notes = "Availability status of the conference room")
    private boolean isAvailable;
}
