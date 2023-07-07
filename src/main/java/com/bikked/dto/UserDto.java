package com.bikked.dto;

import com.bikked.validate.ImageNameValid;
import lombok.*;

import javax.persistence.UniqueConstraint;
import javax.validation.constraints.*;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class UserDto {

    private String userId;

    @NotEmpty
    @Size(min = 2, max = 10, message = "name must be min :2 and max:10")
    private String name;

    @NotEmpty
    @Email(message = "email must be proper format also unique email")
    private String email;

    @NotEmpty
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+])(?=\\S+$).{6,}$",message ="password must be proper format 1.start and end with string 2.at least once Digit,Lower case,Upper case,Special character 3.whitespace no allowed 4.at least six places")
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
