package com.universityweb.chathubmobileapi.friend;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record AddFriendRequest(
        @Schema(description = "ID of the sender", example = "xhosjalgaskdfuyoawer")
        String senderId,

        @Schema(description = "ID of the recipient", example = "asdasdflfjasdjh")
        String recipientId,

        @Schema(description = "Time when the friend request was created", example = "2023-04-01T12:00:00")
        LocalDateTime createdTime
) {}