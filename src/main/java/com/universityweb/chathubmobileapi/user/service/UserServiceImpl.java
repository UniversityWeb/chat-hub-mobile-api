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

    /**
     * Lưu người dùng - thêm mới nếu chưa tồn tại, tồn tại thì cập nhật.
     *
     * @param userDTO   :   Người dùng muốn lưu.
     * @return          :   Người dùng đã lưu thành công.
     *
     * Tác giả: Trần Văn An.
     */
    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        User saved = userRepos.save(user);
        return userMapper.toDTO(saved);
    }

    /**
     * Cập nhật trạng thái truy cập của người dùng.
     *
     * @param uid       :   Mã người dùng muốn cập nhật.
     * @param isOnline  :   Trạng thái truy cập.
     * @return          :   Người dùng sau khi cập nhật.
     *
     * Tác giả: Trần Văn An.
     */
    @Override
    public UserDTO updateOnlineStatus(String uid, boolean isOnline) {
        User user = getUserByUid(uid);

        user.setOnline(isOnline);

        User saved = userRepos.save(user);
        return userMapper.toDTO(saved);
    }
    /**
     * Cập nhật email cho người dùng.
     *
     * @param uid   :   Mã người dùng muốn cập nhật.
     * @param email :   Email cập nhật.
     * @return      :   Người dùng sau khi cập nhật.
     *
     * Tác giả: Trần Văn An.
     */
    @Override
    public UserDTO updateEmail(String uid, String email) {
        User user = getUserByUid(uid);

        user.setEmail(email);

        User saved = userRepos.save(user);
        return userMapper.toDTO(saved);
    }
    /**
     * Cập nhật số điện thoại mới.
     *
     * @param uid           :   Mã người dùng muốn cập nhật.
     * @param phoneNumber   :   Số điện thoại sau cập nhật.
     * @return              :   Người dùng sau khi cập nhật.
     *
     * Tác giả: Trần Văn An.
     */
    @Override
    public UserDTO updatePhoneNumber(String uid, String phoneNumber) {
        User user = getUserByUid(uid);

        user.setPhoneNumber(phoneNumber);

        User saved = userRepos.save(user);
        return userMapper.toDTO(saved);
    }
    /**
     * Kiểm tra sự tồn tại của người dùng bằng email.
     *
     * @param email :   Email cần kiểm tra.
     * @return      :   True nếu người dùng tồn tại, ngược lại false.
     *
     * Tác giả: Trần Văn An.
     */
    @Override
    public boolean existsByEmail(String email) {
        return userRepos.existsByEmail(email);
    }
    /**
     * Cập nhật người dùng.
     *
     * @param userDTO   :   Người dùng muốn cập nhật.
     * @return          :   Người dùng sau cập nhật.
     *
     * Tác giả: Trần Văn An.
     */
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
    /**
     * Kiểm tra sự tồn tại của người dùng bằng số điện thoại.
     *
     * @param phoneNumber :   Số điện thoại cần kiểm tra.
     * @return            :   True nếu người dùng tồn tại, ngược lại false.
     *
     * Tác giả: Trần Văn An.
     */
    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepos.existsByPhoneNumber(phoneNumber);
    }
    /**
     * Lấy thông tin người dùng dưới dạng DTO bằng UID.
     *
     * @param uid :   UID của người dùng.
     * @return    :   DTO của người dùng.
     *
     * Tác giả: Trần Văn An.
     */
    @Override
    public UserDTO getUserDTOByUid(String uid) {
        User user = getUserByUid(uid);
        return userMapper.toDTO(user);
    }
    /**
     * Lấy thông tin người dùng bằng UID.
     *
     * @param uid :   UID của người dùng.
     * @return    :   Người dùng.
     *
     * Tác giả: Trần Văn An.
     */
    @Override
    public User getUserByUid(String uid) {
        String msg = "Could not find any user with uid=" + uid;
        return userRepos.findById(uid)
                .orElseThrow(() -> new UserNotFoundException(msg));
    }
    /**
     * Lấy danh sách tất cả người dùng dưới dạng DTO.
     *
     * @return :   Danh sách DTO của người dùng.
     *
     * Tác giả: Trần Văn An.
     */
    @Override
    public List<UserDTO> getAll() {
        List<User> users = getUsers();
        return userMapper.toDTOs(users);
    }
    /**
     * Lấy danh sách tất cả người dùng.
     *
     * @return :   Danh sách người dùng.
     *
     * Tác giả: Trần Văn An.
     */
    @Override
    public List<User> getUsers() {
        return userRepos.findAll();
    }
}
