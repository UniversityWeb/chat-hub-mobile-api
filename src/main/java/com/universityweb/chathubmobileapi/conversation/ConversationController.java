package com.universityweb.chathubmobileapi.conversation;

import com.universityweb.chathubmobileapi.conversation.service.ConversationService;
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

    @GetMapping("/fetch-conversations")
    public ResponseEntity<List<ConversationDTO>> fetchConversations(String userId){
        List<ConversationDTO> conversations = conversationService.findByUserId(userId);
        return ResponseEntity.ok(conversations);
    }

    @PostMapping("/add-new-conversation")
    public ResponseEntity<ConversationDTO> addNewConversation(@RequestBody ConversationDTO conversation){
        ConversationDTO reponse = conversationService.addNew(conversation);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reponse);
    }
}
