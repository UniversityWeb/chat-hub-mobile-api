package com.universityweb.chathubmobileapi.conversation;

import com.universityweb.chathubmobileapi.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "conversations")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Conversation {
    @Id
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private User sender;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private User recipient;

    @Column(name = "sending_time")
    private Date sendingTime;
}
