package com.bikked.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> resourceNotFoundException(ResourceNotFoundException resource) {

        log.error("Initiated request for Resource Not Found Exception give parameter can not be match ! !");

        ApiResponseMessage message = ApiResponseMessage
                .builder()
                .message(resource.getMessage())
                .status(true)
                .success(HttpStatus.NOT_FOUND)
                .build();

        log.error("Completed request for Resource Not Found Exception ! !");

        return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> MethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        log.error("Initiated request for Method Argument Not Valid Exception give parameter can not be fill requirement ! !");

        Map<String, String> map = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();

            map.put(fieldName, message);

        });

        log.error("Completed request for Method Argument Not Valid Exception ! !");

        return new ResponseEntity<Map<String, String>>(map, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(BadApiRequest.class)
    public ResponseEntity<ApiResponseMessage> resourceNotFoundException(BadApiRequest ex) {

        log.error("Bad Api Request ! !");

        ApiResponseMessage message = ApiResponseMessage
                .builder()
                .message(ex.getMessage())
                .status(false)
                .success(HttpStatus.BAD_REQUEST)
                .build();

        log.error("Completed request for Resource Not Found Exception ! !");

        return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.BAD_REQUEST);

    }

}
