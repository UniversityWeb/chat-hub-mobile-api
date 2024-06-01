package com.universityweb.chathubmobileapi.friend;
/**
 * Ngoại lệ được ném ra nếu không tìm thấy yêu cầu kết bạn.
 *
 * Tác giả: Trần Văn An.
 */
public class FriendRequestNotFoundException extends RuntimeException {
    /**
     * Tạo một ngoại lệ mới với một thông báo cụ thể.
     *
     * @param message   :   Thông báo cụ thể cho ngoại lệ.
     *
     * Tác giả: Trần Văn An.
     */
    public FriendRequestNotFoundException(String message) {
        super(message);
    }
}
