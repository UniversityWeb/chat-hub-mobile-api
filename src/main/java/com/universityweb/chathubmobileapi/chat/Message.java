package com.universityweb.chathubmobileapi.chat;

import com.universityweb.chathubmobileapi.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Message implements Serializable {
    @Id
    private String id;

    @Column(columnDefinition = "text")
    private String message;

    @Column(name = "sending-time")
    private LocalDateTime sendingTime;

    @Enumerated(EnumType.STRING)
    private EType type;

    @Enumerated(EnumType.STRING)
    private EVisibility visibility;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private User sender;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private User recipient;

    public enum EType {
        TEXT, IMAGE, VIDEO;
    }

    public enum EVisibility {
        ACTIVE, DELETE, HIDDEN;
    }
}
