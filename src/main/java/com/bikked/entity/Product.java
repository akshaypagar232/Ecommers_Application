package com.bikked.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "products")
@Entity
public class Product extends BaseEntity {

    @Id
    private String productId;
    private String title;
    private Date addedDate;

    @Column(length = 100)
    private String description;
    private long price;
    private long discountedPrice;
    private long quantity;
    private boolean live;
    private boolean stock;
    private String imageProduct;
    @ManyToOne(fetch = FetchType.EAGER)    //EAGER used because when fetch product also fetch category
    private Category category;

}
