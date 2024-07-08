package com.b2bAr.arzoo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "productVariation")
public class ProductVariations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int variationId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productId")
    private ProductsEntity productsEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "attributeId")
    private ProductAttribute productAttribute;

    private List<String> value; // Store comma-separated values
}
