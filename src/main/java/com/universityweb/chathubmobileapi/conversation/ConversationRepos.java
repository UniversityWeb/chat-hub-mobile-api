package com.universityweb.chathubmobileapi.conversation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConversationRepos extends JpaRepository<Conversation, Long> {
    @Query("SELECT c FROM Conversation c WHERE  c.recipient.uid = ?1 OR c.sender.uid = ?1")
    List<Conversation> findByUserId(String userId);

    @Query("SELECT c FROM Conversation  c WHERE (c.sender.uid = ?1 AND c.recipient.uid = ?2) OR (c.sender.uid = ?2 AND c.recipient.uid = ?1)")
    Conversation findBySenderAndRecipientId(String senderId, String recipientId);
}
