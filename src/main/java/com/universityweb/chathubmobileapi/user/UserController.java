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

    @Operation(
            summary = "Update online status of an user",
            description = "Update online status of an existing user by providing the Uid and an online status.",
            responses = {
                    @ApiResponse(
                            description = "Online status updated successfully.",
                            responseCode = "200"
                    )
            }
    )
    @PutMapping("/update-online-status")
    public ResponseEntity<String> updateOnlineStatus(
            @RequestBody
            @Schema(description = "Uid of the user", example = "xhosjalgaskdfuyoawer")
            String uid,

            @RequestBody
            @Schema(description = "Current online status of the user", example = "false")
            boolean isOnline
    ) {
        userService.updateOnlineStatus(uid, isOnline);
        log.info(UserUtils.ONLINE_STATUS_UPDATED_SUCCESSFULLY);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserUtils.ONLINE_STATUS_UPDATED_SUCCESSFULLY);
    }

    @Operation(
            summary = "Update email of an user",
            description = "Update email of an existing user by providing the Uid and an email.",
            responses = {
                    @ApiResponse(
                            description = "Email updated successfully.",
                            responseCode = "200"
                    )
            }
    )
    @PutMapping("/update-email")
    public ResponseEntity<String> updateEmail(
            @RequestBody
            @Schema(description = "Uid of the user", example = "xhosjalgaskdfuyoawer")
            String uid,

            @RequestBody
            @Schema(description = "Email of the user", example = "vanne@gmail.com")
            String email
    ) {
        userService.updateEmail(uid, email);
        log.info(UserUtils.EMAIL_UPDATED_SUCCESSFULLY);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserUtils.EMAIL_UPDATED_SUCCESSFULLY);
    }

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
            @RequestBody
            @Schema(description = "Uid of the user", example = "xhosjalgaskdfuyoawer")
            String uid,

            @RequestBody
            @Schema(description = "Phone number of the user", example = "+84971823491")
            String phoneNumber
    ) {
        userService.updatePhoneNumber(uid, phoneNumber);
        log.info(UserUtils.PHONE_NUMBER_UPDATED_SUCCESSFULLY);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserUtils.PHONE_NUMBER_UPDATED_SUCCESSFULLY);
    }

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
