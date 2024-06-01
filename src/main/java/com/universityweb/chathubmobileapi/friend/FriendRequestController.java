package com.universityweb.chathubmobileapi.friend;

import com.universityweb.chathubmobileapi.friend.request.UpdateFriendStatusRequest;
import com.universityweb.chathubmobileapi.friend.service.FriendRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/friend-requests")
@Tag(name = "Friend Request")
public class FriendRequestController {

    private static final Logger log = LogManager.getLogger(FriendRequestController.class);

    @Autowired
    private FriendRequestService friendRequestService;
    /**
     * Endpoint hêm yêu cầu kết bạn
     *
     * @param addRequest :   Thông tin yêu cầu kết bạn được cung cấp trong phần thân yêu cầu.
     * @return           :   Phản hồi với thông báo xác nhận khi yêu cầu được thêm thành công.
     *
     * Tác giả: Trần Văn An
     */
    @Operation(
            summary = "Add friend request",
            description = "Add a new friend request by providing the necessary details in the request body.",
            responses = {
                    @ApiResponse(
                            description = "Friend request added successfully.",
                            responseCode = "201"
                    )
            }
    )
    @PostMapping("/add-friend-request")
    public ResponseEntity<String> addFriendRequest(@RequestBody AddFriendRequest addRequest) {
        friendRequestService.addFriendRequest(addRequest);
        log.info(FriendRequestUtils.FRIEND_REQUEST_ADDED_SUCCESSFULLY);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(FriendRequestUtils.FRIEND_REQUEST_ADDED_SUCCESSFULLY);
    }

    /**
     * Cập nhật trạng thái của yêu cầu kết bạn
     *
     * @param updateRequest :   Thông tin yêu cầu cập nhật trạng thái.
     * @return              :   Phản hồi với thông báo xác nhận khi trạng thái được cập nhật thành công.
     *
     * Tác giả: Trần Văn An
     */
    @Operation(
            summary = "Update status of friend request",
            description = "Update the status of an existing friend request by providing the request ID along with the new status.",
            responses = {
                    @ApiResponse(
                            description = "Friend request status updated successfully.",
                            responseCode = "200"
                    )
            }
    )
    @PutMapping("/update-friend-request-status")
    public ResponseEntity<String> updateFriendRequestStatus(
            @RequestBody UpdateFriendStatusRequest updateRequest) {

        friendRequestService.updateFriendRequestStatus(updateRequest);
        log.info(FriendRequestUtils.FRIEND_REQUEST_STATUS_UPDATED_SUCCESSFULLY);
        return ResponseEntity.ok(FriendRequestUtils.FRIEND_REQUEST_STATUS_UPDATED_SUCCESSFULLY);
    }
    /**
     * Xóa yêu cầu kết bạn
     *
     * @param friendRequestId :   ID của yêu cầu kết bạn cần xóa.
     * @return                :   Phản hồi với thông báo xác nhận khi yêu cầu được xóa thành công.
     *
     * Tác giả: Trần Văn An
     */
    @Operation(
            summary = "Delete friend request",
            description = "Delete an existing friend request by providing the request ID.",
            responses = {
                    @ApiResponse(
                            description = "Friend request deleted successfully.",
                            responseCode = "200"
                    )
            }
    )
    @DeleteMapping("/delete-friend-request/{friendRequestId}")
    public ResponseEntity<String> delete(
            @PathVariable("friendRequestId")
            @Schema(description = "ID of the friend request", example = "asdasdflfjasdjh")
            String friendRequestId
    ) {
        friendRequestService.delete(friendRequestId);
        log.info(FriendRequestUtils.FRIEND_REQUEST_DELETED_SUCCESSFULLY);
        return ResponseEntity.ok(FriendRequestUtils.FRIEND_REQUEST_DELETED_SUCCESSFULLY);
    }


    /**
     * Lấy danh sách các yêu cầu kết bạn đang chờ duyệt dựa trên ID của người gửi
     *
     * @param senderId :   ID của người gửi.
     * @return          :   Danh sách các yêu cầu kết bạn đang chờ duyệt.
     *
     * Tác giả: Trần Văn An
     */
    @Operation(
            summary = "Get pending friend requests",
            description = "Get pending friend requests by providing the sender ID.",
            responses = {
                    @ApiResponse(
                            description = "Success.",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("/get-pending-friend-requests-by-sender-id/{senderId}")
    public ResponseEntity<List<FriendRequestDTO>> getPendingFriendRequestsBySenderId(
            @PathVariable("senderId")
            @Schema(description = "ID of the sender", example = "asdasdflfjasdjh")
            String senderId
    ) {
        List<FriendRequestDTO> friendRequestDTOs = friendRequestService
                .getPendingFriendRequestsBySenderId(senderId);
        log.info(FriendRequestUtils.RETRIEVED_PENDING_FRIEND_REQUEST_BY_SENDER_ID_MSG, senderId);
        return ResponseEntity.ok(friendRequestDTOs);
    }
    /**
     * Lấy danh sách các yêu cầu kết bạn đang chờ duyệt dựa trên ID của người nhận
     *
     * @param recipientId :   ID của người nhận.
     * @return            :   Danh sách các yêu cầu kết bạn đang chờ duyệt.
     *
     * Tác giả: Trần Văn An
     */
    @Operation(
            summary = "Get pending friend requests",
            description = "Get pending friend requests by providing the recipient ID.",
            responses = {
                    @ApiResponse(
                            description = "Success.",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("/get-pending-friend-requests-by-recipient-id/{recipientId}")
    public ResponseEntity<List<FriendRequestDTO>> getPendingFriendRequestsByRecipientId(
            @PathVariable("recipientId")
            @Schema(description = "ID of the recipient", example = "asdasdflfjasdjh")
            String recipientId
    ) {
        List<FriendRequestDTO> friendRequestDTOs = friendRequestService
                .getPendingFriendRequestsByRecipientId(recipientId);
        log.info(FriendRequestUtils.RETRIEVED_PENDING_FRIEND_REQUEST_BY_RECIPIENT_ID_MSG, recipientId);
        return ResponseEntity.ok(friendRequestDTOs);
    }
    /**
     * Lấy yêu cầu kết bạn theo ID
     *
     * @param friendRequestId :   ID của yêu cầu kết bạn cần lấy thông tin.
     * @return                :   Phản hồi với thông tin của yêu cầu kết bạn.
     *
     * Tác giả: Trần Văn An
     */
    @Operation(
            summary = "Get friend request",
            description = "Get a existing friend request by providing the request ID.",
            responses = {
                    @ApiResponse(
                            description = "Success.",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("/get-friend-request-by-id/{friendRequestId}")
    public ResponseEntity<FriendRequestDTO> getFriendRequest(
            @PathVariable("friendRequestId")
            @Schema(description = "ID of the friend request", example = "asdasdflfjasdjh")
            String friendRequestId
    ) {
        FriendRequestDTO friendRequestDTO = friendRequestService.getFriendRequestDTO(friendRequestId);
        log.info(FriendRequestUtils.RETRIEVED_FRIEND_REQUEST_BY_ID, friendRequestId);
        return ResponseEntity.ok(friendRequestDTO);
    }
    /**
     * Lấy danh sách các yêu cầu kết bạn đã được chấp nhận dựa trên ID của người dùng
     *
     * @param userId :   ID của người dùng.
     * @return       :   Danh sách các yêu cầu kết bạn đã được chấp nhận.
     *
     * Tác giả: Trần Văn An
     */
    @Operation(
            summary = "Get accepted friend requests",
            description = "Get accepted friend requests by providing the user ID.",
            responses = {
                    @ApiResponse(
                            description = "Success.",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("/get-accepted-friend-requests/{userId}")
    public ResponseEntity<List<FriendRequestDTO>> getAcceptedFriendRequests(
            @PathVariable("userId")
            @Schema(description = "Uid of the user", example = "xhosjalgaskdfuyoawer")
            String userId
    ) {
        List<FriendRequestDTO> friendRequestDTOs = friendRequestService.getAcceptedFriendRequests(userId);
        log.info(FriendRequestUtils.RETRIEVED_ACCEPTED_FRIEND_REQUESTS_MSG, userId);
        return ResponseEntity.ok(friendRequestDTOs);
    }

    /**
     * Lấy danh sách các yêu cầu kết bạn được đề xuất dựa trên ID của người dùng và số lượng tối đa
     *
     * @param userId      :   ID của người dùng.
     * @param maxQuantity :   Số lượng tối đa của các yêu cầu kết bạn được đề xuất.
     * @return            :   Danh sách các yêu cầu kết bạn được đề xuất.
     *
     * Tác giả: Trần Văn An
     */
    @Operation(
            summary = "Get recommended friend requests",
            description = "Get recommended friend requests by providing the user ID.",
            responses = {
                    @ApiResponse(
                            description = "Success.",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("/get-recommended-friend-requests/{userId}")
    public ResponseEntity<List<FriendRequestDTO>> getRecommendedFriendRequests(
            @PathVariable("userId")
            @Schema(description = "Uid of the user", example = "xhosjalgaskdfuyoawer")
            String userId,

            @RequestParam("maxQuantity")
            @Schema(description = "The quantity of recommended friends", example = "10")
            @Min(value = 10)
            int maxQuantity
    ) {
        List<FriendRequestDTO> friendRequestDTOs = friendRequestService.getRecommendedFriendRequests(userId, maxQuantity);
        log.info(FriendRequestUtils.RETRIEVED_RECOMMENDED_FRIEND_REQUESTS_MSG, userId);
        return ResponseEntity.ok(friendRequestDTOs);
    }
}
