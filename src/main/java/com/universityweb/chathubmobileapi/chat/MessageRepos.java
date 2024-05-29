package com.universityweb.chathubmobileapi.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepos extends JpaRepository<Message, String> {
    /**
     * Truy vấn để lấy tất cả các tin nhắn liên quan đến một người dùng cụ thể.
     *
     * @param userUid ID duy nhất của người dùng (có thể là người gửi hoặc người nhận tin nhắn).
     * @return Danh sách các đối tượng Message liên quan đến người dùng.
     * Tác giả: Văn Hoàng
     */
    @Query("SELECT m FROM Message m WHERE m.sender.uid = :userUid OR m.recipient.uid = :userUid")
    List<Message> findAllByUserUid(String userUid);
}
