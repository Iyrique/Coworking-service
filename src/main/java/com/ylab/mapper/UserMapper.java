package com.ylab.mapper;

import com.ylab.dto.UserDTO;
import com.ylab.model.User;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper interface for converting User entities to UserDTOs and vice versa.
 */
@Mapper(componentModel = "spring")
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

    /**
     * Converts a list of User entities to a list of UserDTOs.
     *
     * @param user the list of User entities to convert
     * @return the converted list of UserDTOs
     */
    List<UserDTO> toDtoList(List<User> user);

    /**
     * Converts a list of UserDTOs to a list of User entities.
     *
     * @param userDTOs the list of UserDTOs to convert
     * @return the converted list of User entities
     */
    List<User> toEntityList(List<UserDTO> userDTOs);
}
