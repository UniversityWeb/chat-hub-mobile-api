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

    /**
     * Endpoint để lấy tin nhắn của một người dùng cụ thể.
     *
     * @param userUid ID duy nhất của người dùng mà tin nhắn sẽ được lấy.
     * @return Một ResponseEntity chứa danh sách các đối tượng MessageDTO.
     * Tác giả: Văn Hoàng
     */
    @GetMapping("/fetch-messages")
    public ResponseEntity<List<MessageDTO>> fetchMessages(String userUid) {
        // Ghi nhật ký yêu cầu lấy tin nhắn
        log.info("Lấy tin nhắn cho người dùng: {}", userUid);

        // Lấy tin nhắn cho ID người dùng đã cho và chuyển đổi chúng thành các đối tượng MessageDTO
        List<MessageDTO> messageDTOs = messageService.findByUserUid(userUid, MessageDTO.class);

        // Trả về các tin nhắn đã lấy với trạng thái HTTP 200 OK
        return ResponseEntity.ok(messageDTOs);
    }

    /**
     * Endpoint để gửi một tin nhắn mới.
     *
     * @param messageDTO Đối tượng truyền dữ liệu chứa chi tiết của tin nhắn sẽ được gửi.
     * @return Một ResponseEntity chứa đối tượng MessageDTO đã gửi và trạng thái HTTP 201 Created.
     * Tác giả: Văn Hoàng
     */
    @PostMapping("/send-message")
    public ResponseEntity<MessageDTO> sendMessage(@RequestBody @Valid MessageDTO messageDTO) {
        // Ghi nhật ký yêu cầu gửi một tin nhắn mới
        log.info("Gửi tin nhắn: {}", messageDTO);

        // Gửi tin nhắn sử dụng messageService và nhận lại đối tượng MessageDTO phản hồi
        MessageDTO response = messageService.sendMessage(messageDTO);

        // Trả về tin nhắn đã gửi với trạng thái HTTP 201 Created
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
}
