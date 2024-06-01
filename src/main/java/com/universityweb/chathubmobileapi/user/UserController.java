package com.universityweb.chathubmobileapi.user;

import com.universityweb.chathubmobileapi.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User")
public class UserController {

    private static final Logger log = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    /**
     * Endpoint thêm người dùng mới.
     *
     * @param userDTO   :   Người dùng mới muốn thêm.
     * @return          :   Người dùng sau khi thêm thành công.
     *
     * Tác giả: Trần văn An.
     */
    @Operation(
            summary = "Add user",
            description = "Add a new user by providing the necessary details in the request body.",
            responses = {
                    @ApiResponse(
                            description = "User added successfully.",
                            responseCode = "201"
                    )
            }
    )
    @PostMapping("/add-user")
    public ResponseEntity<String> addUser(@RequestBody @Valid UserDTO userDTO) {
        userService.save(userDTO);
        log.info(UserUtils.USER_ADDED_SUCCESSFULLY);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserUtils.USER_ADDED_SUCCESSFULLY);
    }

    /**
     * Endpoint cập nhật người dùng.
     *
     * @param userDTO   :   người dùng muốn cập nhật.
     * @return          :   người dùng sau khi cập nhật.
     *
     * Tác giả: Trần Văn An.
     */
    @Operation(
            summary = "Update user",
            description = "Update an existing user by providing the necessary details in the request body.",
            responses = {
                    @ApiResponse(
                            description = "User updated successfully.",
                            responseCode = "200"
                    )
            }
    )
    @PutMapping("/update-user")
    public ResponseEntity<String> updateUser(@RequestBody @Valid UserDTO userDTO) {
        userService.update(userDTO);
        log.info(UserUtils.USER_UPDATED_SUCCESSFULLY);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserUtils.USER_UPDATED_SUCCESSFULLY);
    }

    /**
     * Endpoint cập nhật số điện thoại người dùng bằng id người dùng.
     *
     * @param uid           :   Id của người dùng muốn cập nhật.
     * @param phoneNumber   :   Số điện thoại muốn cập nhật.
     * @return              :   Thông báo kết quả thực hiện.
     *
     * Tác giả: Trần Văn An.
     */
    @Operation(
            summary = "Update phone number of an user",
            description = "Update phone number of an existing user by providing the Uid and a phone number.",
            responses = {
                    @ApiResponse(
                            description = "Phone number updated successfully.",
                            responseCode = "200"
                    )
            }
    )
    @PutMapping("/update-phone-number")
    public ResponseEntity<String> updatePhoneNumber(
            @RequestParam
            @Schema(description = "Uid of the user", example = "xhosjalgaskdfuyoawer")
            String uid,

            @RequestParam
            @Schema(description = "Phone number of the user", example = "+84971823491")
            String phoneNumber
    ) {
        userService.updatePhoneNumber(uid, phoneNumber);
        log.info(UserUtils.PHONE_NUMBER_UPDATED_SUCCESSFULLY);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserUtils.PHONE_NUMBER_UPDATED_SUCCESSFULLY);
    }

    /**
     * Endpoint kiểm tra mail sự tồn tại của người dùng bằng email.
     *
     * @param email     :   Email muốn kiểm tra.
     * @return          :   Trả về phản hồi với kết quả tìm kiếm.
     *
     * Tác giả: Trần Văn An.
     */
    @Operation(
            summary = "Check if user exists by email",
            description = "Check if a user exists with the specified email address.",
            responses = {
                    @ApiResponse(
                            description = "User exists.",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("/exists-by-email")
    public ResponseEntity<Boolean> existsByEmail(
            @RequestParam
            @Schema(description = "Email address of the user", example = "example@example.com")
            String email
    ) {
        boolean exists = userService.existsByEmail(email);
        if (exists) {
            log.info(UserUtils.USER_EXISTS_MSG, email);
        } else {
            log.info(UserUtils.USER_NOT_EXISTS_MSG, email);
        }
        return ResponseEntity.ok(exists);
    }

    /**
     * Endpoint kiểm tra sự tồn tại của người dùng bằng số điện thoại.
     *
     * @param phoneNumber   :   Số điện thoại muốn kiểm tra.
     * @return              :   Trả về phàn hồi với kết quả tìm kiếm.
     *
     * Tác giả: Trần Văn An.
     */
    @Operation(
            summary = "Check if user exists by phone number",
            description = "Check if a user exists with the specified phone number.",
            responses = {
                    @ApiResponse(
                            description = "User exists.",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("/exists-by-phone-number")
    public ResponseEntity<Boolean> existsByPhoneNumber(
            @RequestParam
            @Schema(description = "Phone number of the user", example = "+84974567890")
            String phoneNumber
    ) {
        boolean exists = userService.existsByPhoneNumber(phoneNumber);
        if (exists) {
            log.info(UserUtils.USER_WITH_PHONE_EXISTS_MSG, phoneNumber);
        } else {
            log.info(UserUtils.USER_WITH_PHONE_NOT_EXISTS_MSG, phoneNumber);
        }
        return ResponseEntity.ok(exists);
    }

    /**
     * Endpoint tìm kiếm người dùng bằng Id.
     *
     * @param uid   :   Id người dùng muốn tìm kiếm.
     * @return      :   Nguời dùng tm kiếm
     *
     * Tác giả: Trần Văn An.
     */
    @Operation(
            summary = "Check if user exists by phone number",
            description = "Check if a user exists with the specified phone number.",
            responses = {
                    @ApiResponse(
                            description = "Success.",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("/get-user-by-uid/{uid}")
    public ResponseEntity<UserDTO> getUserDtoByUid(
            @PathVariable("uid")
            @Schema(description = "Uid of the user", example = "xhosjalgaskdfuyoawer")
            String uid
    ) {
        UserDTO userDTO = userService.getUserDTOByUid(uid);
        log.info(UserUtils.USER_RETRIEVED_MSG, uid);
        return ResponseEntity.ok(userDTO);
    }

    /**
     * Endpoint lấy tất cả người dùng trong hệ thống.
     *
     * @return  :   Danh sách các người dùng trong hệ thống.
     *
     * Tác giả: Trần Văn An.
     */
    @Operation(
            summary = "Retrieve all",
            description = "Retrieve all users.",
            responses = {
                    @ApiResponse(
                            description = "Success.",
                            responseCode = "200"
                    )
            }
    )
    @GetMapping("/get-all")
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserDTO> userDTOs =  userService.getAll();
        log.info(UserUtils.RETRIEVED_ALL_USERS, userDTOs.size());
        return ResponseEntity.ok(userDTOs);
    }
}
