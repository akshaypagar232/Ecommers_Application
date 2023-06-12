package com.bikked.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private String categoryId;

    @NotEmpty()
    @Size(min = 5,max = 10,message = "title must be min :5 and max:10")
    private String title;

    @NotEmpty(message = "description is empty")
    private String description;

    @NotEmpty(message = "cover image is empty")
    private String coverImage;


}
