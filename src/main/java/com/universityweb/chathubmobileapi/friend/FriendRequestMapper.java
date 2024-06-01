package com.universityweb.chathubmobileapi.friend;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FriendRequestMapper {
    FriendRequestMapper INSTANCE = Mappers.getMapper(FriendRequestMapper.class);
    /**
     * Chuyển đổi một đối tượng FriendRequest thành một đối tượng FriendRequestDTO.
     *
     * @param friendRequest  :   Đối tượng FriendRequest cần chuyển đổi.
     * @return      :   Đối tượng FriendRequestDTO đã chuyển đổi.
     *
     * Tác giả: Trần Văn An.
     */
    FriendRequestDTO toDTO(FriendRequest friendRequest);

    /**
     * Chuyển đổi một danh sách các đối tượng FriendRequest thành một danh sách các đối tượng FriendRequestDTO.
     *
     * @param friendRequests :   Danh sách các đối tượng FriendRequest cần chuyển đổi.
     * @return              :   Danh sách các đối tượng FriendRequestDTO đã chuyển đổi.
     *
     * Tác giả: Trần Văn An.
     */
    List<FriendRequestDTO> toDTOs(List<FriendRequest> friendRequests);
    /**
     * Chuyển đổi một đối tượng FriendRequestDTO thành một đối tượng FriendRequest.
     *
     * @param friendRequestDTO  :   Đối tượng FriendRequestDTO cần chuyển đổi.
     * @return      :   Đối tượng FriendRequest đã chuyển đổi.
     *
     * Tác giả: Trần Văn An.
     */
    FriendRequest toEntity(FriendRequestDTO friendRequestDTO);
    /**
     * Chuyển đổi một danh sách các đối tượng FriendRequestDTO thành một danh sách các đối tượng FriendRequest.
     *
     * @param friendRequestDTOS :   Danh sách các đối tượng FriendRequestDTO cần chuyển đổi.
     * @return              :   Danh sách các đối tượng FriendRequest đã chuyển đổi.
     *
     * Tác giả: Trần Văn An.
     */
    List<FriendRequest> toEntities(List<FriendRequestDTO> friendRequestDTOS);
}
