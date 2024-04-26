package com.universityweb.chathubmobileapi.user;

import com.universityweb.chathubmobileapi.friend.FriendRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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

    @Column(name = "phone_number", unique = true)
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

    @OneToMany(mappedBy = "sender", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<FriendRequest> friendRequestSenders;

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<FriendRequest> friendRequestRecipients;

    public enum EGender {
        MALE, FEMALE, OTHER
    }
}
