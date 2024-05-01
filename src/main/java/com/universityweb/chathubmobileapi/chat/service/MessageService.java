package com.universityweb.chathubmobileapi.chat.service;

import com.universityweb.chathubmobileapi.chat.MessageDTO;

import java.util.List;

public interface MessageService {
    <T> List<T> findByUserUid(String userUid, Class<T> returnType);

    MessageDTO sendMessage(MessageDTO messageDTO);
}
