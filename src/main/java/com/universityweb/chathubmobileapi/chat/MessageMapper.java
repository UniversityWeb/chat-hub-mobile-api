package com.universityweb.chathubmobileapi.chat;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    @Mapping(target = "senderId", expression = "java(message.getSender().getUid())")
    @Mapping(target = "recipientId", expression = "java(message.getRecipient().getUid())")
    MessageDTO toDTO(Message message);

    List<MessageDTO> toDTOs(List<Message> messages);

    @Mapping(target = "sender", ignore = true)
    @Mapping(target = "recipient", ignore = true)
    Message toEntity(MessageDTO messageDTO);

    List<Message> toEntities(List<MessageDTO> messageDTOs);
}
