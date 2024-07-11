package com.ylab.mapper;

import com.ylab.dto.UserDTO;
import com.ylab.model.User;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting User entities to UserDTOs and vice versa.
 */
@Mapper
public interface UserMapper {

    /**
     * Converts a User entity to a UserDTO.
     *
     * @param user the User entity to convert
     * @return the converted UserDTO
     */
    UserDTO toDto(User user);

    /**
     * Converts a UserDTO to a User entity.
     *
     * @param userDTO the UserDTO to convert
     * @return the converted User entity
     */
    User toEntity(UserDTO userDTO);
}
