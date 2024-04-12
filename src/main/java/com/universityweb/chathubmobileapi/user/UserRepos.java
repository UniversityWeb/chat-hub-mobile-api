package com.universityweb.chathubmobileapi.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepos extends JpaRepository<User, String> {
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
}
