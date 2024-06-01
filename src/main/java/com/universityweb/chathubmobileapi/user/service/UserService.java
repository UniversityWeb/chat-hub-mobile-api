package com.universityweb.chathubmobileapi.user.service;

import com.universityweb.chathubmobileapi.user.User;
import com.universityweb.chathubmobileapi.user.UserDTO;

import java.util.List;

/**
 * Interface khai báo các phương thức tương tác với user.
 *
 * Tác giả: Trần Văn An.
 */

public interface UserService {
    /**
     * Lưu người dùng - thêm mới nếu chưa tồn tại, tồn tại thì cập nhật.
     *
     * @param userDTO   :   Người dùng muốn lưu.
     * @return          :   Người dùng đã lưu thành công.
     *
     * Tác giả: Trần Văn An.
     */
    UserDTO save(UserDTO userDTO);

    /**
     * Cập nhật trạng thái truy cập của người dùng.
     *
     * @param uid       :   Mã người dùng muốn cập nhật.
     * @param isOnline  :   Trạng thái truy cập.
     * @return          :   Người dùng sau khi cập nhật.
     *
     * Tác giả: Trần Văn An.
     */
    UserDTO updateOnlineStatus(String uid, boolean isOnline);

    /**
     * Cập nhật email cho người dùng.
     *
     * @param uid   :   Mã người dùng muốn cập nhật.
     * @param email :   Email cập nhật.
     * @return      :   Người dùng sau khi cập nhật.
     *
     * Tác giả: Trần Văn An.
     */
    UserDTO updateEmail(String uid, String email);

    /**
     * Cập nhật số điện thoại mới.
     *
     * @param uid           :   Mã người dùng muốn cập nhật.
     * @param phoneNumber   :   Số điện thoại sau cập nhật.
     * @return              :   Người dùng sau khi cập nhật.
     *
     * Tác giả: Trần Văn An.
     */
    UserDTO updatePhoneNumber(String uid, String phoneNumber);

    /**
     * Kiểm tra sự tồn tại của người dùng bằng email.
     *
     * @param email :   Email cần kiểm tra.
     * @return      :   True nếu người dùng tồn tại, ngược lại false.
     *
     * Tác giả: Trần Văn An.
     */
    boolean existsByEmail(String email);

    /**
     * Cập nhật người dùng.
     *
     * @param userDTO   :   Người dùng muốn cập nhật.
     * @return          :   Người dùng sau cập nhật.
     *
     * Tác giả: Trần Văn An.
     */
    UserDTO update(UserDTO userDTO);

    /**
     * Kiểm tra sự tồn tại của người dùng bằng số điện thoại.
     *
     * @param phoneNumber :   Số điện thoại cần kiểm tra.
     * @return            :   True nếu người dùng tồn tại, ngược lại false.
     *
     * Tác giả: Trần Văn An.
     */
    boolean existsByPhoneNumber(String phoneNumber);

    /**
     * Lấy thông tin người dùng dưới dạng DTO bằng UID.
     *
     * @param uid :   UID của người dùng.
     * @return    :   DTO của người dùng.
     *
     * Tác giả: Trần Văn An.
     */
    UserDTO getUserDTOByUid(String uid);

    /**
     * Lấy thông tin người dùng bằng UID.
     *
     * @param uid :   UID của người dùng.
     * @return    :   Người dùng.
     *
     * Tác giả: Trần Văn An.
     */
    User getUserByUid(String uid);

    /**
     * Lấy danh sách tất cả người dùng dưới dạng DTO.
     *
     * @return :   Danh sách DTO của người dùng.
     *
     * Tác giả: Trần Văn An.
     */
    List<UserDTO>  getAll();

    /**
     * Lấy danh sách tất cả người dùng.
     *
     * @return :   Danh sách người dùng.
     *
     * Tác giả: Trần Văn An.
     */
    List<User>  getUsers();
}
