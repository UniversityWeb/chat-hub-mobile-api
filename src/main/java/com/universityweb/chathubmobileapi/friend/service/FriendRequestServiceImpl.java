package com.universityweb.chathubmobileapi.friend.service;

import com.universityweb.chathubmobileapi.friend.*;
import com.universityweb.chathubmobileapi.friend.request.UpdateFriendStatusRequest;
import com.universityweb.chathubmobileapi.user.User;
import com.universityweb.chathubmobileapi.user.UserDTO;
import com.universityweb.chathubmobileapi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
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
    public FriendRequestDTO updateFriendRequestStatus(UpdateFriendStatusRequest updateRequest) {
        FriendRequest friendRequest = getFriendRequest(updateRequest.friendRequestId());
        friendRequest.setStatus(updateRequest.status());

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
    public List<FriendRequestDTO> getRecommendedFriendRequests(String curUserId, int maxQuantity) {
        List<User> recommendedFriends = new ArrayList<>();
        List<User> users = userService.getUsers();
        users.removeIf(u -> u.getUid().equals(curUserId));

        int curRecommendedQty = 0;
        int userIndex = 0;
        while (userIndex < users.size() && curRecommendedQty < maxQuantity) {
            User user = users.get(userIndex);
            String userId = user.getUid();
            userIndex++;

            if (isRecommendFriend(curUserId, userId)) {
                recommendedFriends.add(user);
                curRecommendedQty++;
            }
        }

        UserDTO senderDTO = userService.getUserDTOByUid(curUserId);

        return recommendedFriends.stream()
                .map(u -> {
                    String userId = u.getUid();
                    int mutualFriends = getMutualFriends(curUserId, userId);
                    UserDTO recipientDTO = userService.getUserDTOByUid(userId);

                    return FriendRequestDTO.builder()
                            .status(FriendRequest.EStatus.NOT_FOUND)
                            .createdTime(null)
                            .mutualFriends(mutualFriends)
                            .sender(senderDTO)
                            .recipient(recipientDTO)
                            .build();
                })
                .collect(Collectors.toList());
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
        for (int i = 0; i < friendsOfFirstUser.size() - 1; i++) {
            FriendRequest friendOfFirstUser = friendsOfFirstUser.get(i);
            String firstFriendId = getFriendIdFromRequest(friendOfFirstUser, firstUserId);
            for (int j = 0; j < friendsOfSecondUser.size() - 1; j++) {
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

    private boolean isRecommendFriend(String curUserId, String anyUserId) {
        Optional<FriendRequest> friendRequestOpt = getFriendRequest(curUserId, anyUserId);
        return friendRequestOpt.isEmpty();
    }

    private Optional<FriendRequest> getFriendRequest(String curUserId, String anyUserId) {
        Optional<FriendRequest> firstOpt = friendRequestRepos.getFriendRequest(curUserId, anyUserId);
        if (firstOpt.isPresent()) {
            return firstOpt;
        }

        Optional<FriendRequest> secondOpt = friendRequestRepos.getFriendRequest(anyUserId, curUserId);
        if (secondOpt.isPresent()) {
            return firstOpt;
        }

        return Optional.empty();
    }
}
