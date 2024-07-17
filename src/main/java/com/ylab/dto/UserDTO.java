package com.ylab.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ApiModel(description = "Details about the user")
public class UserDTO {

    @ApiModelProperty(notes = "The unique ID of the user")
    private Long id;

    @NotNull
    @Size(min = 3, max = 255)
    @ApiModelProperty(notes = "The username of the user")
    private String username;

    @NotNull
    @Size(min = 6, max = 255)
    @ApiModelProperty(notes = "The password of the user")
    private String password;

    @NotNull
    @Size(min = 2, max = 255)
    @ApiModelProperty(notes = "The name of the user")
    private String name;
}
