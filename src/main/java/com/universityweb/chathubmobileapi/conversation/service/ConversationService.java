package com.universityweb.chathubmobileapi.conversation.service;

import com.universityweb.chathubmobileapi.conversation.ConversationDTO;

import java.util.List;

public interface ConversationService {
    ConversationDTO addNew(ConversationDTO conversationDTO);
    List<ConversationDTO> findByUserId(String userId);
    ConversationDTO findBySenderAndRecipientId(String senderId, String recipientId);
    ConversationDTO update(ConversationDTO conversationDTO);
}
