package com.universityweb.chathubmobileapi.chat.service;

import com.universityweb.chathubmobileapi.chat.*;
import com.universityweb.chathubmobileapi.user.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService{
    private static final Logger log = LogManager.getLogger(MessageServiceImpl.class);

    private final UserService userService;
    private final MessageRepos messageRepos;
    private final MessageMapper messageMapper;

    @Autowired
    public MessageServiceImpl(UserService userService, MessageRepos messageRepos, MessageMapper messageMapper) {
        this.userService = userService;
        this.messageRepos = messageRepos;
        this.messageMapper = messageMapper;
    }

    /**
     * Tìm các tin nhắn theo userUid và chuyển đổi chúng sang kiểu trả về cụ thể.
     *
     * @param userUid ID duy nhất của người dùng để tìm các tin nhắn.
     * @param returnType Kiểu trả về của danh sách tin nhắn.
     * @param <T> Kiểu của đối tượng trả về.
     * @return Danh sách các đối tượng tin nhắn dưới dạng kiểu returnType, hoặc null nếu có lỗi xảy ra.
     * Tác giả: Văn Hoàng
     */
    @Override
    public <T> List<T> findByUserUid(String userUid, Class<T> returnType) {
        try {
            // Lấy tất cả các tin nhắn bởi userUid từ database
            List<Message> messages = messageRepos.findAllByUserUid(userUid);

            // Ghi nhật ký việc truy xuất tin nhắn
            log.info(MessageUtils.MESSAGE_RETRIEVING_AT);

            // Kiểm tra kiểu trả về và chuyển đổi danh sách tin nhắn nếu cần
            return returnType.equals(Message.class)
                    ? (List<T>) messages
                    : messages.stream()
                    .map(messageMapper::toDTO)
                    .map(returnType::cast)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // Ghi nhật ký lỗi nếu có vấn đề xảy ra
            log.info(MessageUtils.MESSAGE_RETRIEVING_ERROR + e.getMessage());
            return null;
        }
    }

    /**
     * Gửi một tin nhắn mới.
     *
     * @param messageDTO Đối tượng truyền dữ liệu chứa chi tiết của tin nhắn sẽ được gửi.
     * @return Đối tượng MessageDTO đã gửi, hoặc null nếu có lỗi xảy ra.
     * Tác giả: Văn Hoàng
     */
    @Override
    public MessageDTO sendMessage(MessageDTO messageDTO) {
        try {
            // Chuyển đổi MessageDTO thành thực thể Message
            Message message = messageMapper.toEntity(messageDTO);

            // Thiết lập người gửi và người nhận từ userService
            message.setSender(userService.getUserByUid(messageDTO.getSenderId()));
            message.setRecipient(userService.getUserByUid(messageDTO.getRecipientId()));

            // Tạo ID ngẫu nhiên cho tin nhắn
            message.setId(UUID.randomUUID().toString());

            // Lưu tin nhắn vào database
            messageRepos.save(message);

            // Ghi nhật ký khi thêm tin nhắn thành công
            log.info(MessageUtils.MESSAGE_ADDED_SUCCESSFULLY);
            return messageDTO;
        } catch (Exception e) {
            // Ghi nhật ký lỗi nếu có vấn đề xảy ra
            log.info(MessageUtils.MESSAGE_ADDED_FAILED + e.getMessage());
            return null;
        }
    }
}
