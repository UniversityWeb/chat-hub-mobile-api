package com.universityweb.chathubmobileapi.friend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRequestRepos extends JpaRepository<FriendRequest, String> {
    /**
     * Lấy danh sách các yêu cầu kết bạn đang chờ duyệt dựa trên ID của người gửi.
     *
     * @param senderId :   ID của người gửi.
     * @return         :   Danh sách các yêu cầu kết bạn đang chờ duyệt.
     *
     * Tác giả: Trần Văn An
     */
    @Query(value = "SELECT fr FROM FriendRequest fr WHERE fr.status = 'PENDING' AND fr.sender.uid = :senderId")
    List<FriendRequest> getPendingFriendRequestsBySenderId(@Param("senderId") String senderId);

    /**
     * Lấy danh sách các yêu cầu kết bạn đang chờ duyệt dựa trên ID của người nhận.
     *
     * @param recipientId :   ID của người nhận.
     * @return            :   Danh sách các yêu cầu kết bạn đang chờ duyệt.
     *
     * Tác giả: Trần Văn An
     */
    @Query(value = "SELECT fr FROM FriendRequest fr WHERE fr.status = 'PENDING' AND fr.recipient.uid = :recipientId")
    List<FriendRequest> getPendingFriendRequestsByRecipientId(@Param("recipientId") String recipientId);
    /**
     * Lấy danh sách các yêu cầu kết bạn đã được chấp nhận dựa trên ID của người dùng.
     *
     * @param userId :   ID của người dùng.
     * @return       :   Danh sách các yêu cầu kết bạn đã được chấp nhận.
     *
     * Tác giả: Trần Văn An
     */
    @Query(value = "SELECT fr FROM FriendRequest fr WHERE fr.status = 'ACCEPTED' " +
            "AND (fr.recipient.uid = :userId OR fr.sender.uid = :userId)")
    List<FriendRequest> getAcceptedFriendRequests(@Param("userId") String userId);
    /**
     * Lấy thông tin của một yêu cầu kết bạn dựa trên ID của người gửi và người nhận.
     *
     * @param senderId    :   ID của người gửi.
     * @param recipientId :   ID của người nhận.
     * @return            :   Thông tin của yêu cầu kết bạn nếu có.
     *
     * Tác giả: Trần Văn An
     */
    @Query(value = "SELECT fr FROM FriendRequest fr " +
            "WHERE fr.sender.uid = :senderId AND fr.recipient.uid = :recipientId")
    Optional<FriendRequest> getFriendRequest(@Param("senderId") String senderId,
                                            @Param("recipientId") String recipientId);
}
