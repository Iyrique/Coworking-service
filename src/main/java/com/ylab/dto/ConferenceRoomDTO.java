package com.ylab.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "Details about the conference room")
public class ConferenceRoomDTO {

    @Schema(description = "The unique ID of the conference room")
    private Long id;

    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    @Schema(description = "The name of the conference room")
    private String name;

    @Schema(description = "Availability status of the conference room")
    private boolean isAvailable;
}
