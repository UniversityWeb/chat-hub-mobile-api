package com.universityweb.chathubmobileapi.conversation;

import com.universityweb.chathubmobileapi.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "conversations")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;

    @Column(name = "last_message")
    private String lastMessage;

    @Column(name = "sending_time")
    private LocalDateTime sendingTime;
}
