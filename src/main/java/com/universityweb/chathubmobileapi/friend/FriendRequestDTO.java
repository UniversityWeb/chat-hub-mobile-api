package com.universityweb.chathubmobileapi.friend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.universityweb.chathubmobileapi.user.UserDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode
public class FriendRequestDTO {
    @Schema(description = "ID of the friend request", example = "asdasdflfjasdjh")
    private String id;

    @Schema(description = "Status of the friend request", example = "PENDING")
    private FriendRequest.EStatus status;

    @Schema(description = "Time when the friend request was created", example = "2023-04-01T12:00:00")
    private LocalDateTime createdTime;

    @Schema(description = "Number of mutual friends", example = "3")
    private int mutualFriends;

    private UserDTO sender;

    private UserDTO recipient;
}
