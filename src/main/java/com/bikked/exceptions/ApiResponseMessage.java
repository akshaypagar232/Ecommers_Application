package com.bikked.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponseMessage {

    String message;

    boolean status;

    HttpStatus success;

}
