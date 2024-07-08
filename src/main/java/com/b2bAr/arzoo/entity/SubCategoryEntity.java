package com.b2bAr.arzoo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "SubCategory")
public class SubCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int subCategoryId;

    private String subCategoryName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private ProductCategories productCategories;

  @OneToMany(mappedBy = "subCategoryEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductsEntity> productsEntity;
}
