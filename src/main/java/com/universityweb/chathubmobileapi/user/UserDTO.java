package com.universityweb.chathubmobileapi.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode
public class UserDTO {
    @Schema(description = "Uid of the user", example = "xhosjalgaskdfuyoawer")
    @JsonProperty("id")
    private String uid;

    @Schema(description = "Full name of the user", example = "John Doe")
    private String fullName;

    @Schema(description = "Email address of the user", example = "john@example.com")
    private String email;

    @Schema(description = "Phone number of the user", example = "+1234567890")
    private String phoneNumber;

    @Schema(description = "Gender of the user", example = "MALE")
    private User.EGender gender;

    @Schema(description = "Birthday of the user", example = "1990-05-15T00:00:00")
    private LocalDateTime birthday;

    @Schema(description = "URL of the user's profile image", example = "https://example.com/profile.jpg")
    private String imageUrl;

    @Schema(description = "Indicates whether the user is currently online", example = "true")
    private boolean isOnline;
}
