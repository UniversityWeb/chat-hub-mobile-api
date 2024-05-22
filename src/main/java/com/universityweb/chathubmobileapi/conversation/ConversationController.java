package com.universityweb.chathubmobileapi.conversation;

import com.universityweb.chathubmobileapi.chat.MessageDTO;
import com.universityweb.chathubmobileapi.conversation.service.ConversationService;
import com.universityweb.chathubmobileapi.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/conversations")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;
    @Autowired
    private UserService userService;

    @GetMapping("/fetch-conversations")
    public ResponseEntity<List<ConversationDTO>> fetchConversations(@RequestParam String userId) {
        List<ConversationDTO> conversations = conversationService.findByUserId(userId);
        return ResponseEntity.ok(conversations);
    }

    @GetMapping("/get-conversation")
    public ResponseEntity<ConversationDTO> getConversation(@RequestParam String senderId, @RequestParam String recipientId) {
        ConversationDTO conversation = conversationService.findBySenderAndRecipientId(senderId, recipientId);
        return ResponseEntity.ok(conversation);
    }

    @PostMapping("/add-new-conversation")
    public ResponseEntity<ConversationDTO> addNewConversation(@RequestBody @Valid ConversationDTO conversation) {
        ConversationDTO response = conversationService.addNew(conversation);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/update-conversation")
    public ResponseEntity<ConversationDTO> updateConversation(@RequestBody @Valid MessageDTO message) {
        ConversationDTO conversation = conversationService.findBySenderAndRecipientId(message.getSenderId(), message.getRecipientId());
        if (conversation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        conversation.setLastMessage(message.getMessage());
        conversation.setSendingTime(message.getSendingTime());
        ConversationDTO response = conversationService.update(conversation);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
