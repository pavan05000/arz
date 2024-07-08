package com.b2bAr.arzoo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "productTable")
public class ProductsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    private String productName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private ProductCategories productCategories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subCategoryId")
    private SubCategoryEntity subCategoryEntity;

    private String image_url;

    private String description;
    private double price;
    private boolean availability_status;
    private int stock;

    @OneToMany(mappedBy ="productsEntity", cascade = CascadeType.ALL)
    private List<ProductVariations> productVariations;

    @OneToMany(mappedBy ="productEntity", cascade = CascadeType.ALL)
    private List<OrdersEntity> ordersEntities;

    @OneToMany(mappedBy ="productsEntity", cascade = CascadeType.ALL)
    private List<CartEntity> cartEntities;
}
