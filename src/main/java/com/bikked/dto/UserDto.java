package com.bikked.dto;

import com.bikked.validate.ImageNameValid;
import lombok.*;

import javax.validation.constraints.*;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserDto {

    @NotBlank
    private String userId;

    @NotEmpty
    @Size(min = 2,max = 10,message = "name must be min :2 and max:10")
    private String name;

    @NotEmpty
    @Email(message = "email must be proper format")
    private String email;

    @NotEmpty
    @Pattern(regexp = "^[A-Za-z0-9]+$",message ="password must be proper format")
    private String password;

    @NotEmpty
    private String gender;

    @NotNull
    private String about;

    //custom validator
    @NotEmpty
    @ImageNameValid
    private String imageName;

}
