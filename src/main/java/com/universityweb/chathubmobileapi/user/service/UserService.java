package com.universityweb.chathubmobileapi.user.service;

import com.universityweb.chathubmobileapi.user.User;
import com.universityweb.chathubmobileapi.user.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO save(UserDTO userDTO);

    UserDTO updateOnlineStatus(String uid, boolean isOnline);

    UserDTO updateEmail(String uid, String email);

    UserDTO updatePhoneNumber(String uid, String phoneNumber);

    boolean existsByEmail(String email);

    UserDTO update(UserDTO userDTO);

    boolean existsByPhoneNumber(String phoneNumber);

    UserDTO getUserDTOByUid(String uid);

    User getUserByUid(String uid);

    List<UserDTO>  getAll();
    List<User>  getUsers();
}
