package com.universityweb.chathubmobileapi.conversation.service;

import com.universityweb.chathubmobileapi.conversation.*;
import com.universityweb.chathubmobileapi.user.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ConversationServiceImpl implements ConversationService{
    private static final Logger log = LogManager.getLogger(ConversationServiceImpl.class);

    @Autowired
    private ConversationRepos repos;

    @Autowired
    private ConversationMapper mapper;

    @Autowired
    private UserService userService;

    @Override
    public ConversationDTO addNew(ConversationDTO conversationDTO) {
        try {
            Conversation conversation = mapper.toEntity(conversationDTO);

            conversation.setSender(userService.getUserByUid(conversationDTO.getSenderId()));
            conversation.setRecipient(userService.getUserByUid(conversationDTO.getRecipientId()));
            conversation.setSendingTime(conversationDTO.getSendingTime());

            repos.save(conversation);

            log.info(ConversationUtils.MESSAGE_ADDED_SUCCESSFULLY);

            return conversationDTO;
        } catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<ConversationDTO> findByUserId(String userId) {
        List<Conversation> conversations = repos.findByUserId(userId);
        if (conversations == null || conversations.isEmpty()) {
            return Collections.emptyList();
        }
        return mapper.toDTOs(conversations);
    }

    @Override
    public ConversationDTO findBySenderAndRecipientId(String senderId, String recipientId) {
        Conversation conversation = repos.findBySenderAndRecipientId(senderId, recipientId);
        return mapper.toDTO(conversation);
    }

    @Override
    public ConversationDTO update(ConversationDTO conversationDTO) {
        Conversation conversation = repos.findBySenderAndRecipientId(conversationDTO.getSenderId(), conversationDTO.getRecipientId());
        conversation.setLastMessage(conversation.getLastMessage());
        conversation.setSendingTime(conversationDTO.getSendingTime());
        try {
            return mapper.toDTO(repos.save(conversation));
        } catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }
}
