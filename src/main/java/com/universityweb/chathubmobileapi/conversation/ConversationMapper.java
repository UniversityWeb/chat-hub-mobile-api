package com.universityweb.chathubmobileapi.conversation;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ConversationMapper {
    ConversationMapper INSTANCE = Mappers.getMapper(ConversationMapper.class);

    /**
     * Chuyển đổi một đối tượng Conversation thành một đối tượng ConversationDTO.
     *
     * @param conversation  :   Đối tượng Conversation cần chuyển đổi.
     * @return              :   Đối tượng ConversationDTO đã chuyển đổi.
     *
     * Tác giả: Nguyễn Hà Quỳnh Giao.
     */
    @Mapping(target = "senderId", expression = "java(conversation.getSender().getUid())")
    @Mapping(target = "recipientId", expression = "java(conversation.getRecipient().getUid())")
    ConversationDTO toDTO(Conversation conversation);

    /**
     * Chuyển đổi một danh sách các đối tượng Conversation thành một danh sách các đối tượng ConversationDTO.
     *
     * @param conversations :   Danh sách các đối tượng Conversation cần chuyển đổi.
     * @return              :   Danh sách các đối tượng ConversationDTO đã chuyển đổi.
     *
     * Tác giả: Nguyễn Hà Quỳnh Giao.
     */
    List<ConversationDTO> toDTOs(List<Conversation> conversations);

    /**
     * Chuyển đổi một đối tượng ConversationDTO thành một đối tượng Conversation.
     *
     * @param conversationDTO   :   Đối tượng ConversationDTO cần chuyển đổi.
     * @return                  :   Đối tượng Conversation đã chuyển đổi.
     *
     * Tác giả: Nguyễn Hà Quỳnh Giao.
     */
    Conversation toEntity(ConversationDTO conversationDTO);

    /**
     * Chuyển đổi một danh sách các đối tượng ConversationDTO thành một danh sách các đối tượng Conversation.
     *
     * @param conversationDTOs  :   Danh sách các đối tượng ConversationDTO cần chuyển đổi.
     * @return                  :   Danh sách các đối tượng Conversation đã chuyển đổi.
     *
     * Tác giả: Nguyễn Hà Quỳnh Giao.
     */
    List<Conversation> toEntities(List<ConversationDTO> conversationDTOs);
}
