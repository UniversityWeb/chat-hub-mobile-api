package com.universityweb.chathubmobileapi.conversation;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ConversationDTO {
    private String id;
    @NotNull
    private String senderId;
    @NotNull
    private String recipientId;
    private LocalDateTime sendingTime;
    private String lastMessage;

    public ConversationDTO(String senderId, String recipientId, LocalDateTime sendingTime, String lastMessage) {
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.sendingTime = sendingTime;
        this.lastMessage = lastMessage;
    }
}
