package com.ylab.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Schema(description = "Details about the user")
public class UserDTO {

    @Schema(description = "The unique ID of the user")
    private Long id;

    @NotNull
    @Size(min = 3, max = 255)
    @Schema(description = "The username of the user")
    private String username;

    @NotNull
    @Size(min = 6, max = 255)
    @Schema(description = "The password of the user")
    private String password;

    @NotNull
    @Size(min = 2, max = 255)
    @Schema(description = "The name of the user")
    private String name;
}
