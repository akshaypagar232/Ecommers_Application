package com.bikked.dto;

import com.bikked.entity.Category;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {


    private String productId;
    private Date addedDate;
    @NotEmpty
    @Size(max = 100, message = "description must be max : 100")
    private String title;
    @NotEmpty
    private String description;
    @NotNull
    private long price;
    @NotNull                               // data type long you should be not used NotEmpty
    private long discountedPrice;
    @NotEmpty
    private String imageProduct;
    @NotNull
    private long quantity;
    @NotNull
    private boolean live;
    @NotNull
    private boolean stock;

    private CategoryDto categoryDto;

}



