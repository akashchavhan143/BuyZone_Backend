package com.ecommerce.storageUtility;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String education;

    @Column(length = 1000) // For long Cloudinary URLs
    private String profileImageUrl;
}