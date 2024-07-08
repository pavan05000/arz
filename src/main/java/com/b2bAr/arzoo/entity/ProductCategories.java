package com.b2bAr.arzoo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "product_categories")
public class ProductCategories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;
    private String categoryName;
    private String description;


    @OneToMany(mappedBy = "productCategories", cascade = CascadeType.ALL)
    private List<SubCategoryEntity> subCategories;

    @OneToMany(mappedBy = "productCategories", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductsEntity> productsEntity;
}

