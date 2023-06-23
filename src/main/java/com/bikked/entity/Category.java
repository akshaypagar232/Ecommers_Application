package com.bikked.entity;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Categorys")
public class Category extends BaseEntity {

    @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String categoryId;

    @Column(name = "category_title", nullable = false, length = 50)
    private String title;

    @Column(name = "category_description", length = 200)
    private String description;

    @Column(name = "cover_image")
    private String coverImage;

    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY,cascade = CascadeType.ALL)      //LAZY used because when fetch category then not fetch product
    private List<Product> products = new ArrayList<>();
}
