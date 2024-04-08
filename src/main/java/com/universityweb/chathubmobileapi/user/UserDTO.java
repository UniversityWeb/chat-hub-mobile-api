package com.universityweb.chathubmobileapi.user;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode
public class UserDTO {
    private String uid;
    private String fullName;
    private String email;
    private String phoneNumber;
    private User.EGender gender;
    private LocalDateTime birthday;
    private String imageUrl;
    private boolean isOnline;
}
