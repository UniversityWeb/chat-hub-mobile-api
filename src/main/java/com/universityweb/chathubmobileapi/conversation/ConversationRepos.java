package com.universityweb.chathubmobileapi.conversation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConversationRepos extends JpaRepository<Conversation, Long> {
    /**
     * Truy vấn để lấy danh sách cuộc hội thoại theo của một người dùng.
     *
     * @param userId    :   Mã người dng cần lấy.
     * @return          :   Danh sách các đối tượng Conversation liên quan đến người dùng.
     *
     * Tác giả: Nguyễn Hà Quỳnh Giao.
     */
    @Query("SELECT c FROM Conversation c WHERE  c.recipient.uid = ?1 OR c.sender.uid = ?1")
    List<Conversation> findByUserId(String userId);

    /**
     * Truy vấn để lấy cuộc hội thoại theo với sự tham gia của 2 người dùng.
     *
     * @param senderId      :   Id người dùng thứ nhất.
     * @param recipientId   :   Id người dùng thứ hai.
     * @return              :   Một tượng Conversation có sự tham gia cuẩ 2 người dùng.
     *
     * Tác giả: Nguyễn Hà Quỳnh Giao.
     */
    @Query("SELECT c FROM Conversation  c WHERE (c.sender.uid = ?1 AND c.recipient.uid = ?2) OR (c.sender.uid = ?2 AND c.recipient.uid = ?1)")
    Conversation findBySenderAndRecipientId(String senderId, String recipientId);
}
