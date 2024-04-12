package com.universityweb.chathubmobileapi.friend;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FriendRequestMapper {
    FriendRequestMapper INSTANCE = Mappers.getMapper(FriendRequestMapper.class);

    FriendRequestDTO toDTO(FriendRequest friendRequest);

    List<FriendRequestDTO> toDTOs(List<FriendRequest> friendRequests);

    FriendRequest toEntity(FriendRequestDTO friendRequestDTO);

    List<FriendRequest> toEntities(List<FriendRequestDTO> friendRequestDTOS);
}
