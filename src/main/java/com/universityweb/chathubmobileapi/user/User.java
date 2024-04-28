package com.universityweb.chathubmobileapi.user;

import com.universityweb.chathubmobileapi.chat.Message;
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

    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private EGender gender;

    private LocalDateTime birthday;

    @Column(name = "is_online")
    private boolean isOnline;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<FriendRequest> friendRequestSenders;

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<FriendRequest> friendRequestRecipients;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Message> messageSenders;

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Message> messageRecipients;

    public enum EGender {
        MALE, FEMALE, OTHER
    }
}
