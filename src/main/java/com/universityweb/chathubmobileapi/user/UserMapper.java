package com.universityweb.chathubmobileapi.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(User user);

    List<UserDTO> toDTOs(List<User> users);

    User toEntity(UserDTO userDTO);

    List<User> toEntities(List<UserDTO> userDTOs);
}
