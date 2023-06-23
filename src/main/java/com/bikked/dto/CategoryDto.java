package com.bikked.dto;

import com.bikked.entity.Product;
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
    @Size(max = 50,message = "title must be max:10")
    private String title;

    @NotEmpty(message = "description is empty")
    private String description;

    @NotEmpty(message = "cover image is empty")
    private String coverImage;

    private Product product;

}
