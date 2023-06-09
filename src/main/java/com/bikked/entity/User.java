package com.bikked.entity;

import lombok.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_email",unique = true)
    private String email;

    @Column(name = "user_password",length = 7)
    private String password;

    @Column(name = "gender")
    private String gender;

    @Column(length = 1000)
    private String about;

    @Column(name = "image_name")
    private String imageName;

}
