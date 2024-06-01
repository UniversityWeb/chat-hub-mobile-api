package com.universityweb.chathubmobileapi.friend.service;

import com.universityweb.chathubmobileapi.friend.AddFriendRequest;
import com.universityweb.chathubmobileapi.friend.FriendRequest;
import com.universityweb.chathubmobileapi.friend.FriendRequestDTO;
import com.universityweb.chathubmobileapi.friend.request.UpdateFriendStatusRequest;

import java.util.List;

/**
 * Interface khai báo các phương thức tương tác với friend request.
 *
 * Tác giả: Trần Văn An.
 */
public interface FriendRequestService {
    /**
     * Thêm yêu cầu kết bạn mới.
     *
     * @param addRequest :   Thông tin yêu cầu kết bạn cần thêm.
     * @return           :   Thông tin của yêu cầu kết bạn đã được thêm.
     *
     * Tác giả: Trần Văn An
     */
    FriendRequestDTO addFriendRequest(AddFriendRequest addRequest);
    /**
     * Cập nhật trạng thái của yêu cầu kết bạn.
     *
     * @param updateRequest :   Thông tin yêu cầu cập nhật trạng thái.
     * @return              :   Thông tin của yêu cầu kết bạn đã được cập nhật.
     *
     * Tác giả: Trần Văn An
     */
    FriendRequestDTO updateFriendRequestStatus(UpdateFriendStatusRequest updateRequest);

    /**
     * Xóa yêu cầu kết bạn.
     *
     * @param friendRequestId :   ID của yêu cầu kết bạn cần xóa.
     *
     * Tác giả: Trần Văn An
     */
    void delete(String friendRequestId);
    /**
     * Lấy danh sách các yêu cầu kết bạn đang chờ duyệt dựa trên ID của người gửi.
     *
     * @param senderId :   ID của người gửi.
     * @return         :   Danh sách các yêu cầu kết bạn đang chờ duyệt.
     *
     * Tác giả: Trần Văn An
     */
    List<FriendRequestDTO> getPendingFriendRequestsBySenderId(String senderId);
    /**
     * Lấy danh sách các yêu cầu kết bạn đang chờ duyệt dựa trên ID của người nhận.
     *
     * @param recipientId :   ID của người nhận.
     * @return            :   Danh sách các yêu cầu kết bạn đang chờ duyệt.
     *
     * Tác giả: Trần Văn An
     */
    List<FriendRequestDTO> getPendingFriendRequestsByRecipientId(String recipientId);
    /**
     * Lấy thông tin của một yêu cầu kết bạn dựa trên ID của yêu cầu.
     *
     * @param friendRequestId :   ID của yêu cầu kết bạn cần lấy thông tin.
     * @return                :   Thông tin của yêu cầu kết bạn.
     *
     * Tác giả: Trần Văn An
     */
    FriendRequestDTO getFriendRequestDTO(String friendRequestId);
    /**
     * Lấy danh sách các yêu cầu kết bạn đã được chấp nhận dựa trên ID của người dùng.
     *
     * @param userId :   ID của người dùng.
     * @return       :   Danh sách các yêu cầu kết bạn đã được chấp nhận.
     *
     * Tác giả: Trần Văn An
     */
    List<FriendRequestDTO> getAcceptedFriendRequests(String userId);
    /**
     * Lấy danh sách các yêu cầu kết bạn được đề xuất dựa trên ID của người dùng và số lượng tối đa.
     *
     * @param userId      :   ID của người dùng.
     * @param maxQuantity :   Số lượng tối đa của các yêu cầu kết bạn được đề xuất.
     * @return            :   Danh sách các yêu cầu kết bạn được đề xuất.
     *
     * Tác giả: Trần Văn An
     */
    List<FriendRequestDTO> getRecommendedFriendRequests(String userId, int maxQuantity);
}
