package com.b2bAr.arzoo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "productAttribute")
public class ProductAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int attributeId;

    private String attributeName;
    private String attributeType;

    @OneToMany(mappedBy ="productAttribute", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductVariations> productVariations;
}
