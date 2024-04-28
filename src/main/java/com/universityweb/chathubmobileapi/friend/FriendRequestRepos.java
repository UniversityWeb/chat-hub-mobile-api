package com.universityweb.chathubmobileapi.friend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRequestRepos extends JpaRepository<FriendRequest, String> {
    @Query(value = "SELECT fr FROM FriendRequest fr WHERE fr.status = 'PENDING' AND fr.sender.uid = :senderId")
    List<FriendRequest> getPendingFriendRequestsBySenderId(@Param("senderId") String senderId);

    @Query(value = "SELECT fr FROM FriendRequest fr WHERE fr.status = 'PENDING' AND fr.recipient.uid = :recipientId")
    List<FriendRequest> getPendingFriendRequestsByRecipientId(@Param("recipientId") String recipientId);

    @Query(value = "SELECT fr FROM FriendRequest fr WHERE fr.status = 'ACCEPTED' " +
            "AND (fr.recipient.uid = :userId OR fr.sender.uid = :userId)")
    List<FriendRequest> getAcceptedFriendRequests(@Param("userId") String userId);

    @Query(value = "SELECT fr FROM FriendRequest fr " +
            "WHERE fr.sender.uid = :senderId AND fr.recipient.uid = :recipientId")
    Optional<FriendRequest> getFriendRequest(@Param("senderId") String senderId,
                                            @Param("recipientId") String recipientId);
}
