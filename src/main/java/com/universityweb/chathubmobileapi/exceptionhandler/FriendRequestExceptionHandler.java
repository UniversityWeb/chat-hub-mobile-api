package com.universityweb.chathubmobileapi.exceptionhandler;

import com.universityweb.chathubmobileapi.friend.FriendRequestNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class FriendRequestExceptionHandler {
    private static final Logger log = LogManager.getLogger(FriendRequestExceptionHandler.class);

    @ExceptionHandler(FriendRequestNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(FriendRequestNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage(), LocalDateTime.now());
        log.error("Friend request not found", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }
}
