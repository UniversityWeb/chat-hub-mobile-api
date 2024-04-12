package com.universityweb.chathubmobileapi.friend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRequestRepos extends JpaRepository<FriendRequest, String> {
    @Query(value = "SELECT fr FROM FriendRequest fr WHERE fr.status = 'PENDING' AND fr.sender.uid = :senderId")
    List<FriendRequest> getPendingFriendRequestsBySenderId(@Param("senderId") String senderId);

    @Query(value = "SELECT fr FROM FriendRequest fr WHERE fr.status = 'PENDING' AND fr.recipient.uid = :recipientId")
    List<FriendRequest> getPendingFriendRequestsByRecipientId(@Param("recipientId") String recipientId);

    @Query(value = "SELECT fr FROM FriendRequest fr WHERE fr.status = 'ACCEPTED' " +
            "AND (fr.recipient.uid = :userId OR fr.sender.uid = :userId)")
    List<FriendRequest> getAcceptedFriendRequests(@Param("userId") String userId);

    // TODO
    @Query(value = "SELECT fr FROM FriendRequest fr WHERE fr.status = 'PENDING' AND fr.recipient.uid = :userId")
    List<FriendRequest> getRecommendedFriends(@Param("userId") String userId);
}
