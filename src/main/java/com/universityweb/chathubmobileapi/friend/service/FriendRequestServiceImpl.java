package com.universityweb.chathubmobileapi.friend.service;

import com.universityweb.chathubmobileapi.friend.*;
import com.universityweb.chathubmobileapi.user.User;
import com.universityweb.chathubmobileapi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendRequestServiceImpl implements FriendRequestService {
    private final FriendRequestMapper frMapper = FriendRequestMapper.INSTANCE;
    private final FriendRequestRepos friendRequestRepos;
    private final UserService userService;

    @Override
    public FriendRequestDTO addFriendRequest(AddFriendRequest addRequest) {
        User sender = userService.getUserByUid(addRequest.senderId());
        User recipient = userService.getUserByUid(addRequest.recipientId());

        FriendRequest friendRequest = FriendRequest.builder()
                .sender(sender)
                .recipient(recipient)
                .status(FriendRequest.EStatus.PENDING)
                .createdTime(addRequest.createdTime())
                .build();
        return save(friendRequest);
    }

    @Override
    public FriendRequestDTO updateFriendRequestStatus(String friendRequestId, FriendRequest.EStatus status) {
        FriendRequest friendRequest = getFriendRequest(friendRequestId);
        friendRequest.setStatus(status);

        return save(friendRequest);
    }

    @Override
    public void delete(String friendRequestId) {
        friendRequestRepos.deleteById(friendRequestId);
    }

    @Override
    public List<FriendRequestDTO> getPendingFriendRequestsBySenderId(String senderId) {
        List<FriendRequest> friendRequests = friendRequestRepos.getPendingFriendRequestsBySenderId(senderId);
        List<FriendRequestDTO> friendRequestDTOs = frMapper.toDTOs(friendRequests);

        List<FriendRequestDTO> updatedFriendRequestDTOs = friendRequestDTOs.stream()
                .map(this::attachMutualFriendsToFriendRequestDTO)
                .collect(Collectors.toList());

        return updatedFriendRequestDTOs;
    }

    @Override
    public List<FriendRequestDTO> getPendingFriendRequestsByRecipientId(String recipientId) {
        List<FriendRequest> friendRequests = friendRequestRepos.getPendingFriendRequestsByRecipientId(recipientId);
        List<FriendRequestDTO> friendRequestDTOs = frMapper.toDTOs(friendRequests);

        List<FriendRequestDTO> updatedFriendRequestDTOs = friendRequestDTOs.stream()
                .map(this::attachMutualFriendsToFriendRequestDTO)
                .collect(Collectors.toList());

        return updatedFriendRequestDTOs;
    }

    @Override
    public FriendRequestDTO getFriendRequestDTO(String friendRequestId) {
        FriendRequest friendRequest = getFriendRequest(friendRequestId);
        return frMapper.toDTO(friendRequest);
    }

    @Override
    public List<FriendRequestDTO> getAcceptedFriendRequests(String userId) {
        List<FriendRequest> friendRequests = friendRequestRepos.getAcceptedFriendRequests(userId);
        List<FriendRequestDTO> friendRequestDTOs = frMapper.toDTOs(friendRequests);

        List<FriendRequestDTO> updatedFriendRequestDTOs = friendRequestDTOs.stream()
                .map(this::attachMutualFriendsToFriendRequestDTO)
                .collect(Collectors.toList());

        return updatedFriendRequestDTOs;
    }

    @Override
    public List<FriendRequestDTO> getRecommendedFriends(String userId) {
        List<FriendRequest> friendRequests = friendRequestRepos.getRecommendedFriends(userId);
        List<FriendRequestDTO> friendRequestDTOs = frMapper.toDTOs(friendRequests);

        List<FriendRequestDTO> updatedFriendRequestDTOs = friendRequestDTOs.stream()
                .map(this::attachMutualFriendsToFriendRequestDTO)
                .collect(Collectors.toList());

        return updatedFriendRequestDTOs;
    }

    private FriendRequestDTO save(FriendRequest friendRequest) {
        FriendRequest saved = friendRequestRepos.save(friendRequest);
        return frMapper.toDTO(saved);
    }

    private FriendRequest getFriendRequest(String friendRequestId) {
        String msg = String.format("Could not find any request with id=%s", friendRequestId);
        return friendRequestRepos.findById(friendRequestId)
                .orElseThrow(() -> new FriendRequestNotFoundException(msg));
    }

    private FriendRequestDTO attachMutualFriendsToFriendRequestDTO(FriendRequestDTO friendRequestDTO) {
        String tempSenderId = friendRequestDTO.getSender().getUid();
        String tempRecipientId = friendRequestDTO.getRecipient().getUid();
        int mutualFriends = getMutualFriends(tempSenderId, tempRecipientId);
        friendRequestDTO.setMutualFriends(mutualFriends);
        return friendRequestDTO;
    }

    private int getMutualFriends(String firstUserId, String secondUserId) {
        List<FriendRequest> friendsOfFirstUser = friendRequestRepos
                .getAcceptedFriendRequests(firstUserId);
        List<FriendRequest> friendsOfSecondUser = friendRequestRepos
                .getAcceptedFriendRequests(secondUserId);

        int count = 0;
        for (int i = 0; i < friendsOfFirstUser.size(); i++) {
            FriendRequest friendOfFirstUser = friendsOfFirstUser.get(i);
            String firstFriendId = getFriendIdFromRequest(friendOfFirstUser, firstUserId);
            for (int j = 0; j < friendsOfSecondUser.size(); j++) {
                FriendRequest friendOfSecondUser = friendsOfSecondUser.get(i);
                String secondFriendId = getFriendIdFromRequest(friendOfSecondUser, secondUserId);
                if (firstFriendId.equals(secondFriendId)) {
                    count++;
                }
            }
        }
        return count;
    }

    private String getFriendIdFromRequest(FriendRequest friendRequest, String curUserId) {
        String senderId = friendRequest.getSender().getUid();
        String recipientId = friendRequest.getRecipient().getUid();
        return senderId.equals(curUserId) ? recipientId : senderId;
    }
}
