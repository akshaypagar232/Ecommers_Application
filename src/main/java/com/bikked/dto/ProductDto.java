package com.bikked.dto;

import com.bikked.entity.Category;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    @NotEmpty
    private String productId;
    @NotEmpty
    private String title;
    @NotEmpty
    @Size(max = 100,message = "description must be max : 100")
    private String description;
    @NotEmpty
    private long price;
    @NotEmpty
    private long discountedPrice;
    @NotEmpty
    private long quantity;
    @NotEmpty
    private Date addedDate;
    @NotEmpty
    private boolean live;
    @NotEmpty
    private boolean stock;
    @NotEmpty
    private String imageProduct;
    private CategoryDto categoryDto;

}



