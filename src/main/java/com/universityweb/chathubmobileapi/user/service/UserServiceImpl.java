package com.universityweb.chathubmobileapi.user.service;

import com.universityweb.chathubmobileapi.user.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Autowired private UserRepos userRepos;

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        User saved = userRepos.save(user);
        return userMapper.toDTO(saved);
    }

    @Override
    public UserDTO updateOnlineStatus(String uid, boolean isOnline) {
        User user = getUserByUid(uid);

        user.setOnline(isOnline);

        User saved = userRepos.save(user);
        return userMapper.toDTO(saved);
    }

    @Override
    public UserDTO updateEmail(String uid, String email) {
        User user = getUserByUid(uid);

        user.setEmail(email);

        User saved = userRepos.save(user);
        return userMapper.toDTO(saved);
    }

    @Override
    public UserDTO updatePhoneNumber(String uid, String phoneNumber) {
        User user = getUserByUid(uid);

        user.setPhoneNumber(phoneNumber);

        User saved = userRepos.save(user);
        return userMapper.toDTO(saved);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepos.existsByEmail(email);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        User user = getUserByUid(userDTO.getUid());

        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setGender(userDTO.getGender());
        user.setBirthday(userDTO.getBirthday());
        user.setOnline(userDTO.isOnline());

        User saved = userRepos.save(user);
        return userMapper.toDTO(saved);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepos.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public UserDTO getUserDTOByUid(String uid) {
        User user = getUserByUid(uid);
        return userMapper.toDTO(user);
    }

    @Override
    public User getUserByUid(String uid) {
        String msg = "Could not find any user with uid=" + uid;
        return userRepos.findById(uid)
                .orElseThrow(() -> new UserNotFoundException(msg));
    }

    @Override
    public List<UserDTO> getAll() {
        List<User> users = getUsers();
        return userMapper.toDTOs(users);
    }

    @Override
    public List<User> getUsers() {
        return userRepos.findAll();
    }
}
