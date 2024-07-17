package com.ylab.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Details about the booking")
public class BookingDTO {

    @ApiModelProperty(notes = "The unique ID of the booking")
    private Long id;

    @NotNull(message = "Username cannot be null")
    @ApiModelProperty(notes = "The username of the person booking the resource")
    private String username;

    @NotNull(message = "Resource ID cannot be null")
    @ApiModelProperty(notes = "The ID of the resource being booked")
    private Integer resourceId;

    @NotNull(message = "Start time cannot be null")
    @ApiModelProperty(notes = "The start time of the booking")
    private LocalDateTime startTime;

    @NotNull(message = "End time cannot be null")
    @ApiModelProperty(notes = "The end time of the booking")
    private LocalDateTime endTime;

    @ApiModelProperty(notes = "Flag indicating if the booking is for a workspace")
    private boolean isWorkspace;
}
