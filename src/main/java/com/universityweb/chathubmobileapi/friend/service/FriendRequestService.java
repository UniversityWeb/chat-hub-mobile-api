package com.universityweb.chathubmobileapi.friend.service;

import com.universityweb.chathubmobileapi.friend.AddFriendRequest;
import com.universityweb.chathubmobileapi.friend.FriendRequest;
import com.universityweb.chathubmobileapi.friend.FriendRequestDTO;

import java.util.List;

public interface FriendRequestService {
    FriendRequestDTO addFriendRequest(AddFriendRequest addRequest);

    FriendRequestDTO updateFriendRequestStatus(String friendRequestId, FriendRequest.EStatus status);

    void delete(String friendRequestId);

    List<FriendRequestDTO> getPendingFriendRequestsBySenderId(String senderId);

    List<FriendRequestDTO> getPendingFriendRequestsByRecipientId(String recipientId);

    FriendRequestDTO getFriendRequestDTO(String friendRequestId);

    List<FriendRequestDTO> getAcceptedFriendRequests(String userId);

    List<FriendRequestDTO> getRecommendedFriendRequests(String userId, int maxQuantity);
}
