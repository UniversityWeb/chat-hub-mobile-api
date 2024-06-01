package com.universityweb.chathubmobileapi.friend.request;

import com.universityweb.chathubmobileapi.friend.FriendRequest;
import io.swagger.v3.oas.annotations.media.Schema;
/**
 * Mô tả yêu cầu cập nhật trạng thái của yêu cầu kết bạn.
 *
 * Tác giả: Trần Văn An.
 */
public record UpdateFriendStatusRequest(
        //ID của yêu cầu kết bạn.
        @Schema(description = "ID of the friend request", example = "asdasdflfjasdjh")
        String friendRequestId,
        //Trạng thái hiện tại của yêu cầu kết bạn.
        @Schema(description = "Current status of the friend request", example = "ACCEPTED")
        FriendRequest.EStatus status
) {}
