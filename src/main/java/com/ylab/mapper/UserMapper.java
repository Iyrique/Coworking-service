package com.ylab.mapper;

import com.ylab.dto.UserDTO;
import com.ylab.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserDTO toDto(User user);
    User toEntity(UserDTO userDTO);
}
