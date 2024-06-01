package com.universityweb.chathubmobileapi.user;

/**
 * Ngoại lệ được ném ra nếu không tìm thấy người dùng.
 *
 * Tác giả: Trần Văn An.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Tạo một ngoại lệ mới với một thông báo cụ thể.
     *
     * @param message   :   Thông báo cụ thể cho ngoại lệ.
     *
     * Tác giả: Trần Văn An.
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
