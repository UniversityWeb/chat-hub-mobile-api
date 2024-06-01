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
    /**
     * Thêm yêu cầu kết bạn mới.
     *
     * @param addRequest :   Thông tin yêu cầu kết bạn cần thêm.
     * @return           :   Thông tin của yêu cầu kết bạn đã được thêm.
     *
     * Tác giả: Trần Văn An
     */
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
    /**
     * Cập nhật trạng thái của yêu cầu kết bạn.
     *
     * @param updateRequest :   Thông tin yêu cầu cập nhật trạng thái.
     * @return              :   Thông tin của yêu cầu kết bạn đã được cập nhật.
     *
     * Tác giả: Trần Văn An
     */
    @Override
    public FriendRequestDTO updateFriendRequestStatus(UpdateFriendStatusRequest updateRequest) {
        FriendRequest friendRequest = getFriendRequest(updateRequest.friendRequestId());
        friendRequest.setStatus(updateRequest.status());

        return save(friendRequest);
    }
    /**
     * Xóa yêu cầu kết bạn.
     *
     * @param friendRequestId :   ID của yêu cầu kết bạn cần xóa.
     *
     * Tác giả: Trần Văn An
     */
    @Override
    public void delete(String friendRequestId) {
        friendRequestRepos.deleteById(friendRequestId);
    }
    /**
     * Lấy danh sách các yêu cầu kết bạn đang chờ duyệt dựa trên ID của người gửi.
     *
     * @param senderId :   ID của người gửi.
     * @return         :   Danh sách các yêu cầu kết bạn đang chờ duyệt.
     *
     * Tác giả: Trần Văn An
     */
    @Override
    public List<FriendRequestDTO> getPendingFriendRequestsBySenderId(String senderId) {
        List<FriendRequest> friendRequests = friendRequestRepos.getPendingFriendRequestsBySenderId(senderId);
        List<FriendRequestDTO> friendRequestDTOs = frMapper.toDTOs(friendRequests);

        List<FriendRequestDTO> updatedFriendRequestDTOs = friendRequestDTOs.stream()
                .map(this::attachMutualFriendsToFriendRequestDTO)
                .collect(Collectors.toList());

        return updatedFriendRequestDTOs;
    }
    /**
     * Lấy danh sách các yêu cầu kết bạn đang chờ duyệt dựa trên ID của người nhận.
     *
     * @param recipientId :   ID của người nhận.
     * @return            :   Danh sách các yêu cầu kết bạn đang chờ duyệt.
     *
     * Tác giả: Trần Văn An
     */
    @Override
    public List<FriendRequestDTO> getPendingFriendRequestsByRecipientId(String recipientId) {
        List<FriendRequest> friendRequests = friendRequestRepos.getPendingFriendRequestsByRecipientId(recipientId);
        List<FriendRequestDTO> friendRequestDTOs = frMapper.toDTOs(friendRequests);

        List<FriendRequestDTO> updatedFriendRequestDTOs = friendRequestDTOs.stream()
                .map(this::attachMutualFriendsToFriendRequestDTO)
                .collect(Collectors.toList());

        return updatedFriendRequestDTOs;
    }
    /**
     * Lấy thông tin của một yêu cầu kết bạn dựa trên ID của yêu cầu.
     *
     * @param friendRequestId :   ID của yêu cầu kết bạn cần lấy thông tin.
     * @return                :   Thông tin của yêu cầu kết bạn.
     *
     * Tác giả: Trần Văn An
     */
    @Override
    public FriendRequestDTO getFriendRequestDTO(String friendRequestId) {
        FriendRequest friendRequest = getFriendRequest(friendRequestId);
        return frMapper.toDTO(friendRequest);
    }
    /**
     * Lấy danh sách các yêu cầu kết bạn đã được chấp nhận dựa trên ID của người dùng.
     *
     * @param userId :   ID của người dùng.
     * @return       :   Danh sách các yêu cầu kết bạn đã được chấp nhận.
     *
     * Tác giả: Trần Văn An
     */
    @Override
    public List<FriendRequestDTO> getAcceptedFriendRequests(String userId) {
        List<FriendRequest> friendRequests = friendRequestRepos.getAcceptedFriendRequests(userId);
        List<FriendRequestDTO> friendRequestDTOs = frMapper.toDTOs(friendRequests);

        List<FriendRequestDTO> updatedFriendRequestDTOs = friendRequestDTOs.stream()
                .map(this::attachMutualFriendsToFriendRequestDTO)
                .collect(Collectors.toList());

        return updatedFriendRequestDTOs;
    }
    /**
     * Lấy danh sách các yêu cầu kết bạn được đề xuất dựa trên ID của người dùng và số lượng tối đa.
     *
     * @param curUserId      :   ID của người dùng.
     * @param maxQuantity :   Số lượng tối đa của các yêu cầu kết bạn được đề xuất.
     * @return            :   Danh sách các yêu cầu kết bạn được đề xuất.
     *
     * Tác giả: Trần Văn An
     */
    @Override
    public List<FriendRequestDTO> getRecommendedFriendRequests(String curUserId, int maxQuantity) {
        // Danh sách các người bạn được đề xuất
        List<User> recommendedFriends = new ArrayList<>();
        // Lấy danh sách tất cả người dùng
        List<User> users = userService.getUsers();
        // Loại bỏ người dùng hiện tại khỏi danh sách
        users.removeIf(u -> u.getUid().equals(curUserId));

        // Số lượng người bạn được đề xuất hiện tại
        int curRecommendedQty = 0;
        // Vị trí của người dùng trong danh sách
        int userIndex = 0;
        // Duyệt qua danh sách người dùng
        while (userIndex < users.size() && curRecommendedQty < maxQuantity) {
            User user = users.get(userIndex);
            String userId = user.getUid();
            userIndex++;

            // Nếu người này có thể được đề xuất làm bạn
            if (isRecommendFriend(curUserId, userId)) {
                recommendedFriends.add(user);
                curRecommendedQty++;
            }
        }

        // Lấy thông tin người dùng hiện tại
        UserDTO senderDTO = userService.getUserDTOByUid(curUserId);

        // Tạo danh sách yêu cầu kết bạn được đề xuất
        return recommendedFriends.stream()
                .map(u -> {
                    String userId = u.getUid();
                    // Lấy số lượng bạn chung giữa người dùng hiện tại và người được đề xuất
                    int mutualFriends = getMutualFriends(curUserId, userId);
                    // Lấy thông tin người được đề xuất
                    UserDTO recipientDTO = userService.getUserDTOByUid(userId);

                    // Tạo đối tượng FriendRequestDTO
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

    /**
     * Lưu yêu cầu kết bạn.
     *
     * @param friendRequest Yêu cầu kết bạn cần lưu.
     * @return Thông tin của yêu cầu kết bạn đã được lưu.
     *
     * Tác giả: Trần Văn An
     */
    private FriendRequestDTO save(FriendRequest friendRequest) {
        FriendRequest saved = friendRequestRepos.save(friendRequest);
        return frMapper.toDTO(saved);
    }
    /**
     * Lấy yêu cầu kết bạn dựa trên ID.
     *
     * @param friendRequestId ID của yêu cầu kết bạn cần lấy.
     * @return Yêu cầu kết bạn tương ứng với ID.
     * @throws FriendRequestNotFoundException Nếu không tìm thấy yêu cầu kết bạn với ID đã cho.
     *
     * Tác giả: Trần Văn An
     */
    private FriendRequest getFriendRequest(String friendRequestId) {
        String msg = String.format("Could not find any request with id=%s", friendRequestId);
        return friendRequestRepos.findById(friendRequestId)
                .orElseThrow(() -> new FriendRequestNotFoundException(msg));
    }
    /**
     * Gắn kết bạn chung vào DTO của yêu cầu kết bạn.
     *
     * @param friendRequestDTO DTO của yêu cầu kết bạn cần gắn kết bạn chung.
     * @return DTO của yêu cầu kết bạn đã được gắn kết bạn chung.
     *
     * Tác giả: Trần Văn An
     */
    private FriendRequestDTO attachMutualFriendsToFriendRequestDTO(FriendRequestDTO friendRequestDTO) {
        String tempSenderId = friendRequestDTO.getSender().getUid();
        String tempRecipientId = friendRequestDTO.getRecipient().getUid();
        int mutualFriends = getMutualFriends(tempSenderId, tempRecipientId);
        friendRequestDTO.setMutualFriends(mutualFriends);
        return friendRequestDTO;
    }
    /**
     * Lấy số lượng bạn chung giữa hai người dùng.
     *
     * @param firstUserId  ID của người dùng thứ nhất.
     * @param secondUserId ID của người dùng thứ hai.
     * @return Số lượng bạn chung giữa hai người dùng.
     *
     * Tác giả: Trần Văn An
     */
    private int getMutualFriends(String firstUserId, String secondUserId) {
        // Lấy danh sách các yêu cầu kết bạn đã được chấp nhận của người dùng đầu tiên
        List<FriendRequest> friendsOfFirstUser =
                friendRequestRepos.getAcceptedFriendRequests(firstUserId);
        // Lấy danh sách các yêu cầu kết bạn đã được chấp nhận của người dùng thứ hai
        List<FriendRequest> friendsOfSecondUser =
                friendRequestRepos.getAcceptedFriendRequests(secondUserId);

        // Biến đếm số lượng bạn chung
        int count = 0;

        // Duyệt qua danh sách yêu cầu kết bạn của người dùng đầu tiên
        for (int i = 0; i < friendsOfFirstUser.size() - 1; i++) {
            // Lấy ra yêu cầu kết bạn trong danh sách
            FriendRequest friendOfFirstUser = friendsOfFirstUser.get(i);
            // Lấy ID của người bạn từ yêu cầu kết bạn của người dùng đầu tiên
            String firstFriendId = getFriendIdFromRequest(friendOfFirstUser, firstUserId);

            // Duyệt qua danh sách yêu cầu kết bạn của người dùng thứ hai
            for (int j = 0; j < friendsOfSecondUser.size() - 1; j++) {
                // Lấy ra yêu cầu kết bạn trong danh sách
                FriendRequest friendOfSecondUser = friendsOfSecondUser.get(i);
                // Lấy ID của người bạn từ yêu cầu kết bạn của người dùng thứ hai
                String secondFriendId = getFriendIdFromRequest(friendOfSecondUser, secondUserId);

                // Nếu hai người bạn có cùng ID, tăng biến đếm lên
                if (firstFriendId.equals(secondFriendId)) {
                    count++;
                }
            }
        }
        // Trả về số lượng bạn chung
        return count;
    }
    /**
     * Lấy ID của bạn từ yêu cầu kết bạn.
     *
     * @param friendRequest Yêu cầu kết bạn.
     * @param curUserId     ID của người dùng hiện tại.
     * @return ID của bạn trong yêu cầu kết bạn.
     *
     * Tác giả: Trần Văn An
     */
    private String getFriendIdFromRequest(FriendRequest friendRequest, String curUserId) {
        String senderId = friendRequest.getSender().getUid();
        String recipientId = friendRequest.getRecipient().getUid();
        return senderId.equals(curUserId) ? recipientId : senderId;
    }
    /**
     * Kiểm tra liệu có nên đề xuất kết bạn hay không.
     *
     * @param curUserId  ID của người dùng hiện tại.
     * @param anyUserId  ID của một người dùng bất kỳ.
     * @return True nếu không có yêu cầu kết bạn giữa hai người dùng, ngược lại trả về False.
     *
     * Tác giả: Trần Văn An
     */
    private boolean isRecommendFriend(String curUserId, String anyUserId) {
        Optional<FriendRequest> friendRequestOpt = getFriendRequest(curUserId, anyUserId);
        return friendRequestOpt.isEmpty();
    }

    /**
     * Lấy yêu cầu kết bạn dựa trên ID của hai người dùng.
     *
     * @param curUserId ID của người dùng hiện tại.
     * @param anyUserId ID của một người dùng bất kỳ.
     * @return Yêu cầu kết bạn giữa người dùng hiện tại và người dùng bất kỳ, nếu có.
     *
     * Tác giả: Trần Văn An
     */
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
