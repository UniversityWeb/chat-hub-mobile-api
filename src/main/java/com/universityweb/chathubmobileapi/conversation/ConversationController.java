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
    /**
     * Endpoint lấy danh sách hội thoại của người dùng theo Id người dùng.
     *
     * @param userUid   :   ID duy nhất của người dùng.
     * @return          :   Danh sách các cuộc hội thoại có sự tham gia của người dùng có Id l userUid
     *
     * Tác giả: Nguyễn Hà Quỳnh Giao.
     */
    @Autowired
    private ConversationService conversationService;
    @GetMapping("/fetch-conversations/{userId}")
    public ResponseEntity<List<ConversationDTO>> fetchConversations(@PathVariable("userId")
                                                                        @Schema(description = "Uid of the user", example = "xhosjalgaskdfuyoawer")
                                                                        String userId) {
        List<ConversationDTO> conversations = conversationService.findByUserId(userId);
        return ResponseEntity.ok(conversations);
    }

    /**
     * Endpoint lấy dữ liệu một cuộc hội thoại có sự tham gia của 2 người dùng.
     *
     * @param senderId      :   Id người dùng thứ nhất.
     * @param recipientId   :   Id người dùng thứ hai.
     * @return              :   Một cuộc hội thoại có sự tham gia của 2 người dùng
     *                          có id là senderId và recipientId
     *
     * Tác giả: Nguyễn Hà Quỳnh Giao
     */

    @GetMapping("/get-conversation")
    public ResponseEntity<ConversationDTO> getConversation(@RequestParam String senderId, @RequestParam String recipientId) {
        ConversationDTO conversation = conversationService.findBySenderAndRecipientId(senderId, recipientId);
        return ResponseEntity.ok(conversation);
    }

    /**
     * Endpoint thêm mới cuộc hội thoại.
     *
     * @param message   :   Tin nhắn vừa nhận được.
     * @return          :   Cuộc hội thoại vừa thêm mới.
     *
     * Tác giả: Nguyễn Hà Quỳnh Giao
     */
    @PostMapping("/add-new-conversation")
    public ResponseEntity<ConversationDTO> addNewConversation(@RequestBody @Valid MessageDTO message) {
        ConversationDTO conversation = new ConversationDTO(message.getSenderId(), message.getRecipientId(), message.getSendingTime(), message.getMessage());
        ConversationDTO response = conversationService.addNew(conversation);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Endpoint cập nhật cuộc hội thoại.
     *
     * @param message   :   Tin nhắn vừa nhận được.
     * @return          :   Cuộc hội thoại vừa cập nhật.
     *
     * Tác giả: Nguyễn Hà Quỳnh Giao.
     */
    @PutMapping("/update-conversation")
    public ResponseEntity<ConversationDTO> updateConversation(@RequestBody @Valid MessageDTO message) {
        // Tìm kiếm cuộc hội thoại theo senderId và recipientId
        ConversationDTO conversation =
                conversationService.findBySenderAndRecipientId(
                        message.getSenderId(),
                        message.getRecipientId());
        // Nếu cuộc hội thoại không tồn tại thì trả về không thấy.
        if (conversation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // Thiết lập giá trị mới
        conversation.setLastMessage(message.getMessage());
        conversation.setSendingTime(message.getSendingTime());

        ConversationDTO response = conversationService.update(conversation);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
