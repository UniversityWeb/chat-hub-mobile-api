package com.universityweb.chathubmobileapi.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User implements Serializable {
    @Id
    private String uid;

    @Column(name = "full_name")
    private String fullName;

    @Column(unique = true)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private EGender gender;

    private LocalDateTime birthday;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_online")
    private boolean isOnline;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    public enum EGender {
        MALE, FEMALE, OTHER
    }
}
