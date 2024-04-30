package com.universityweb.chathubmobileapi.friend.request;

import com.universityweb.chathubmobileapi.friend.FriendRequest;
import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateFriendStatusRequest(
        @Schema(description = "ID of the friend request", example = "asdasdflfjasdjh")
        String friendRequestId,

        @Schema(description = "Current status of the friend request", example = "ACCEPTED")
        FriendRequest.EStatus status
) {}
