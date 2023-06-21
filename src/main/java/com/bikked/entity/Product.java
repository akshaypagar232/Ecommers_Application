package com.bikked.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "products")
@Entity
public class Product {

    @Id
    private String productId;
    private String title;

    @Column(length = 100)
    private String description;
    private long price;
    private long discountedPrice;
    private long quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;

}
