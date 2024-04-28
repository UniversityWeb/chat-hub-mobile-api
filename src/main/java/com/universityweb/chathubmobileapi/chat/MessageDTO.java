package com.universityweb.chathubmobileapi.chat;

import jakarta.validation.constraints.AssertTrue;
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
public class MessageDTO {

    private String message;

    private LocalDateTime sendingTime;

    private Message.EType type;

    private Message.EVisibility visibility;

    @NotNull
    private String senderId;

    @NotNull
    private String recipientId;

    @AssertTrue(message = MessageUtils.MESSAGE_VALIDATION_ERROR)
    private boolean isSenderIdDifferentFromRecipientId() {
        return !senderId.equals(recipientId);
    }
}
