package com.universityweb.chathubmobileapi.conversation.service;

import com.universityweb.chathubmobileapi.conversation.ConversationDTO;

import java.util.List;

/**
 * Interface khai báo các phương thức tương tác với conversation.
 *
 * Tác giả: Nguyễn Hà Quỳnh Giao.
 */

public interface ConversationService {
    /**
     * Thêm mới cuộc hội thoại
     *
     * @param conversationDTO   :   Cuộc hội thoại muốn thêm mới
     * @return                  :   Cuộc hội thoại vừa thêm mới.
     *
     * Tác giả: Nguyễn hà Quỳnh Giao.
     */
    ConversationDTO addNew(ConversationDTO conversationDTO);
    /**
     * Tìm kiếm danh sách hội thoại theo mã người dùng.
     *
     * @param userId    :   Mã người dùng muốn lấy danh sách.
     * @return          :   Danh sách hội thoại.
     *
     * Tác giả: Nguyễn hà Quỳnh Giao.
     */
    List<ConversationDTO> findByUserId(String userId);
    /**
     * Tìm kiếm cuộc hội thoại theo mã nguời tham gia
     *
     * @param senderId      :   Mã người dùng thứ nhất
     * @param recipientId   :   Mã người dùng thứ hai.
     * @return              :   Cuộc hội thoại
     *
     * Tác giả: Nguyễn hà Quỳnh Giao.
     */
    ConversationDTO findBySenderAndRecipientId(String senderId, String recipientId);
    /**
     * Cập nhật cuộc hội thoại.
     *
     * @param conversationDTO       :   Cuộc hội thoại muốn cập nhật.
     * @return                      :   Cuộc hội thoại
     *
     * Tác giả: Nguyễn hà Quỳnh Giao.
     */
    ConversationDTO update(ConversationDTO conversationDTO);
}
