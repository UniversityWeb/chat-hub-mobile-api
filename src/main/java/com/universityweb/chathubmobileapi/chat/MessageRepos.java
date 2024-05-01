package com.universityweb.chathubmobileapi.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepos extends JpaRepository<Message, String> {
    @Query("SELECT m FROM Message m WHERE m.sender.uid = :userUid OR m.recipient.uid = :userUid")
    List<Message> findAllByUserUid(String userUid);
}
