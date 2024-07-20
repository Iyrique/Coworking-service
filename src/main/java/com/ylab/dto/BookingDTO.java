package com.ylab.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Details about the booking")
public class BookingDTO {

    @Schema(description = "The unique ID of the booking")
    private Long id;

    @NotNull(message = "Username cannot be null")
    @Schema(description = "The username of the person booking the resource")
    private String username;

    @NotNull(message = "Resource ID cannot be null")
    @Schema(description = "The ID of the resource being booked")
    private Integer resourceId;

    @NotNull(message = "Start time cannot be null")
    @Schema(description = "The start time of the booking")
    private LocalDateTime startTime;

    @NotNull(message = "End time cannot be null")
    @Schema(description = "The end time of the booking")
    private LocalDateTime endTime;

    @Schema(description = "Flag indicating if the booking is for a workspace")
    private boolean isWorkspace;
}
