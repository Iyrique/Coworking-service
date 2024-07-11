package com.ylab.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserDTO {

    private Long id;

    @NotNull
    @Size(min = 3, max = 255)
    private String username;

    @NotNull
    @Size(min = 6, max = 255)
    private String password;

    @NotNull
    @Size(min = 2, max = 255)
    private String name;
}
