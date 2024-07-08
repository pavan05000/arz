package com.b2bAr.arzoo.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "cart")
@Data
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "productId")
    private ProductsEntity productsEntity;

    private int cartCount;
}




