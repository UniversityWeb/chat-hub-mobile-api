package com.universityweb.chathubmobileapi.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepos extends JpaRepository<User, String> {
    /**
     * Truy vấn dữ liệu xem email có tổn tại không.
     *
     * @param email :   Email cần kiểm tra
     * @return      :   Một boolean trả về kết quả tìm kiếm. 0 - không tìm thấy, 1 - tìm thấy.
     *
     * Tác giả: Trần Văn An.
     */
    boolean existsByEmail(String email);

    /**
     * Truy vn dữ liệu xem số điện thoại có tồn tại không.
     *
     * @param phoneNumber   :   Số điện thoại cần kiểm tra.
     * @return              :   Một boolean trả về kết quả tìm kiếm. 0 - không tìm thấy, 1 - tìm thấy.
     *
     * Tác giả: Trần Văn An.
     */
    boolean existsByPhoneNumber(String phoneNumber);
}
