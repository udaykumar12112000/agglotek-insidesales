package com.agglotek.insidesales.exception;

import com.agglotek.insidesales.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException ex) {
        return new ResponseEntity<>(getErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(getErrorResponse("Internal server error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, Object> getErrorResponse(String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("error", message);
        return body;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = "Data integrity error";

        if (ex.getCause() != null) {
            String causeMsg = ex.getCause().getMessage();
            if (causeMsg.contains("agglo_user_id_key")) {
                message = "Agglo User ID already exists.";
            } else if(causeMsg.contains("email_key")){
                message = "Email already exists.";
            } else if (causeMsg.contains("not-null")) {
                message = "Required fields are missing.";
            } else if(causeMsg.contains("unique_role_name")){
                message = "Role Already exists.";
            }
        }

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("status", false, "message", message));
    }
}
