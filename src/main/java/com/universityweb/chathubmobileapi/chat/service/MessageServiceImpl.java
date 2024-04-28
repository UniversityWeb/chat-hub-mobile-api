package com.universityweb.chathubmobileapi.chat.service;

import com.universityweb.chathubmobileapi.chat.*;
import com.universityweb.chathubmobileapi.user.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService{
    private static final Logger log = LogManager.getLogger(MessageServiceImpl.class);

    private final UserService userService;

    private final MessageRepos messageRepos;
    private final MessageMapper messageMapper;

    @Autowired
    public MessageServiceImpl(UserService userService, MessageRepos messageRepos, MessageMapper messageMapper) {
        this.userService = userService;
        this.messageRepos = messageRepos;
        this.messageMapper = messageMapper;
    }

    @Override
    public <T> List<T> findByUserUid(String userUid, Class<T> returnType) {
        try {
            List<Message> messages = messageRepos.findAllByUserUid(userUid);

            log.info(MessageUtils.MESSAGE_RETRIEVING_AT);

            return returnType.equals(Message.class)
                    ? (List<T>) messages
                    : messages.stream()
                    .map(messageMapper::toDTO)
                    .map(returnType::cast)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.info(MessageUtils.MESSAGE_RETRIEVING_ERROR + e.getMessage());
            return null;
        }
    }

    @Override
    public String sendMessage(MessageDTO messageDTO) {
        try {
            Message message = messageMapper.toEntity(messageDTO);

            message.setSender(userService.getUserByUid(messageDTO.getSenderId()));
            message.setRecipient(userService.getUserByUid(messageDTO.getRecipientId()));
            message.setId(UUID.randomUUID().toString());

            messageRepos.save(message);

            log.info(MessageUtils.MESSAGE_ADDED_SUCCESSFULLY);
            return MessageUtils.MESSAGE_ADDED_SUCCESSFULLY;
        } catch (Exception e) {
            log.info(MessageUtils.MESSAGE_ADDED_FAILED + e.getMessage());
            return MessageUtils.MESSAGE_ADDED_FAILED + e.getMessage();
        }
    }
}
