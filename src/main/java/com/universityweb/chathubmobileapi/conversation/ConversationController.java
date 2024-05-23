package com.universityweb.chathubmobileapi.conversation;

import com.universityweb.chathubmobileapi.chat.MessageDTO;
import com.universityweb.chathubmobileapi.conversation.service.ConversationService;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @GetMapping("/fetch-conversations/{userId}")
    public ResponseEntity<List<ConversationDTO>> fetchConversations(@PathVariable("userId")
                                                                        @Schema(description = "Uid of the user", example = "xhosjalgaskdfuyoawer")
                                                                        String userId) {
        List<ConversationDTO> conversations = conversationService.findByUserId(userId);
        return ResponseEntity.ok(conversations);
    }

    @GetMapping("/get-conversation")
    public ResponseEntity<ConversationDTO> getConversation(@RequestParam String senderId, @RequestParam String recipientId) {
        ConversationDTO conversation = conversationService.findBySenderAndRecipientId(senderId, recipientId);
        return ResponseEntity.ok(conversation);
    }

    @PostMapping("/add-new-conversation")
    public ResponseEntity<ConversationDTO> addNewConversation(@RequestBody @Valid MessageDTO message) {
        ConversationDTO conversation = new ConversationDTO(message.getSenderId(), message.getRecipientId(), message.getSendingTime(), message.getMessage());
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
