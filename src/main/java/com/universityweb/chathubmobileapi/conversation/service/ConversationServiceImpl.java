package com.universityweb.chathubmobileapi.conversation.service;

import com.universityweb.chathubmobileapi.conversation.*;
import com.universityweb.chathubmobileapi.user.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationServiceImpl implements ConversationService{
    private static final Logger log = LogManager.getLogger(ConversationServiceImpl.class);

    @Autowired
    private ConversationRepos repos;

    @Autowired
    private ConversationMapper mapper;

    @Autowired
    private UserService userService;

    /**
     * Thêm mới cuộc hội thoại
     *
     * @param conversationDTO   :   Cuộc hội thoại muốn thêm mới
     * @return                  :   Cuộc hội thoại vừa thêm mới.
     *
     * Tác giả: Nguyễn hà Quỳnh Giao.
     */
    @Override
    public ConversationDTO addNew(ConversationDTO conversationDTO) {
            // Chuyển sang Entity
            Conversation conversation = mapper.toEntity(conversationDTO);

            // Thiết lập giá trị
            conversation.setSender(userService.getUserByUid(conversationDTO.getSenderId()));
            conversation.setRecipient(userService.getUserByUid(conversationDTO.getRecipientId()));
            conversation.setSendingTime(conversationDTO.getSendingTime());

            Conversation saved = repos.save(conversation);
            log.info(ConversationUtils.MESSAGE_ADDED_SUCCESSFULLY);

            return mapper.toDTO(saved);
    }

    /**
     * Tìm kiếm danh sách hội thoại theo mã người dùng.
     *
     * @param userId    :   Mã người dùng muốn lấy danh sách.
     * @return          :   Danh sách hội thoại.
     *
     * Tác giả: Nguyễn hà Quỳnh Giao.
     */
    @Override
    public List<ConversationDTO> findByUserId(String userId) {
        List<Conversation> conversations = repos.findByUserId(userId);
        return mapper.toDTOs(conversations);
    }
    /**
     * Tìm kiếm cuộc hội thoại theo mã nguời tham gia
     *
     * @param senderId      :   Mã người dùng thứ nhất
     * @param recipientId   :   Mã người dùng thứ hai.
     * @return              :   Cuộc hội thoại
     *
     * Tác giả: Nguyễn hà Quỳnh Giao.
     */
    @Override
    public ConversationDTO findBySenderAndRecipientId(String senderId, String recipientId) {
        Conversation conversation = repos.findBySenderAndRecipientId(senderId, recipientId);
        return mapper.toDTO(conversation);
    }
    /**
     * Cập nhật cuộc hội thoại.
     *
     * @param conversationDTO       :   Cuộc hội thoại muốn cập nhật.
     * @return                      :   Cuộc hội thoại
     *
     * Tác giả: Nguyễn hà Quỳnh Giao.
     */
    @Override
    public ConversationDTO update(ConversationDTO conversationDTO) {
        Conversation conversation = repos.findBySenderAndRecipientId(conversationDTO.getSenderId(), conversationDTO.getRecipientId());
        conversation.setLastMessage(conversationDTO.getLastMessage());
        conversation.setSendingTime(conversationDTO.getSendingTime());
        try {
            return mapper.toDTO(repos.save(conversation));
        } catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }
}
