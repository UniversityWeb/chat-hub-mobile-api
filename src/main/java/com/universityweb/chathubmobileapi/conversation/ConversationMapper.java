package com.universityweb.chathubmobileapi.conversation;

import com.universityweb.chathubmobileapi.friend.FriendRequest;
import com.universityweb.chathubmobileapi.friend.FriendRequestDTO;
import com.universityweb.chathubmobileapi.friend.FriendRequestMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ConversationMapper {
    ConversationMapper INSTANCE = Mappers.getMapper(ConversationMapper.class);
    ConversationDTO toDTO(Conversation conversation);
    List<ConversationDTO> toDTOs(List<Conversation> friendRequests);
    Conversation toEntity(ConversationDTO friendRequestDTO);
    List<Conversation> toEntities(List<ConversationDTO> friendRequestDTOS);
}
