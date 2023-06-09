package com.bikked.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageResponse {

    private String imageName;

    private String message;

    private boolean status;

    private HttpStatus success;

}
