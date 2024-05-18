package com.universityweb.chathubmobileapi.conversation;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ConversationDTO {
    @NotNull
    private String senderId;
    @NotNull
    private String recipientId;
    private Date sendingTime;
    private String imgUrl;
    private String conversationName;
    private String lastMessage;
}
