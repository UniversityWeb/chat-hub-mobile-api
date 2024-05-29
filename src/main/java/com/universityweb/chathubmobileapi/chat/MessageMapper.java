package com.universityweb.chathubmobileapi.chat;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    /**
     * Chuyển đổi một đối tượng Message thành một đối tượng MessageDTO.
     *
     * @param message Đối tượng Message cần chuyển đổi.
     * @return Đối tượng MessageDTO đã chuyển đổi.
     * Tác giả: Văn Hoàng
     */
    @Mapping(target = "senderId", expression = "java(message.getSender().getUid())")
    @Mapping(target = "recipientId", expression = "java(message.getRecipient().getUid())")
    MessageDTO toDTO(Message message);

    /**
     * Chuyển đổi một danh sách các đối tượng Message thành một danh sách các đối tượng MessageDTO.
     *
     * @param messages Danh sách các đối tượng Message cần chuyển đổi.
     * @return Danh sách các đối tượng MessageDTO đã chuyển đổi.
     * Tác giả: Văn Hoàng
     */
    List<MessageDTO> toDTOs(List<Message> messages);

    /**
     * Chuyển đổi một đối tượng MessageDTO thành một đối tượng Message.
     *
     * @param messageDTO Đối tượng MessageDTO cần chuyển đổi.
     * @return Đối tượng Message đã chuyển đổi.
     * Tác giả: Văn Hoàng
     */
    @Mapping(target = "sender", ignore = true)
    @Mapping(target = "recipient", ignore = true)
    Message toEntity(MessageDTO messageDTO);

    /**
     * Chuyển đổi một danh sách các đối tượng MessageDTO thành một danh sách các đối tượng Message.
     *
     * @param messageDTOs Danh sách các đối tượng MessageDTO cần chuyển đổi.
     * @return Danh sách các đối tượng Message đã chuyển đổi.
     * Tác giả: Văn Hoàng
     */
    List<Message> toEntities(List<MessageDTO> messageDTOs);
}
