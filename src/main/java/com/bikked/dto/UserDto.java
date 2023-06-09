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

    private String userId;

    @NotEmpty()
    @Size(min = 3,max = 10,message = "name must be min :3 and max:10")
    private String name;

    @Email(message = "email must be proper format")
    @NotEmpty(message = "Email is empty")
    private String email;

    @NotEmpty
    @Pattern(regexp = "^[A-Za-z0-9]+$",message ="password must be proper format")
    private String password;

    @NotEmpty(message = "gender is selected")
    private String gender;

    @NotNull(message = "about field is empty !!")
    private String about;

    //custom validator
    @ImageNameValid
    private String imageName;

}
