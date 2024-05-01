package com.universityweb.chathubmobileapi.chat;

import com.universityweb.chathubmobileapi.chat.service.MessageService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {
    private static final Logger log = LogManager.getLogger(MessageController.class);

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/fetch-messages")
    public ResponseEntity<List<MessageDTO>> fetchMessages(String userUid) {
        List<MessageDTO> messageDTOs = messageService.findByUserUid(userUid, MessageDTO.class);
        return ResponseEntity.ok(messageDTOs);
    }

    @PostMapping("/send-message")
    public ResponseEntity<MessageDTO> sendMessage(@RequestBody @Valid MessageDTO messageDTO) {
        MessageDTO response = messageService.sendMessage(messageDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
}
