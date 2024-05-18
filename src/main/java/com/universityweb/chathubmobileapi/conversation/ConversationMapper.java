package com.universityweb.chathubmobileapi.conversation;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ConversationMapper {
    ConversationMapper INSTANCE = Mappers.getMapper(ConversationMapper.class);
    ConversationDTO toDTO(Conversation conversation);
    List<ConversationDTO> toDTOs(List<Conversation> conversations);
    Conversation toEntity(ConversationDTO conversationDTO);
    List<Conversation> toEntities(List<ConversationDTO> conversationDTOs);
}
