package com.bikked.entity;

import lombok.*;

import javax.persistence.*;

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

    @Column(name = "category_title")
    private String title;

    @Column(name = "category_description")
    private String description;

    @Column(name = "cover_image")
    private String coverImage;

}
