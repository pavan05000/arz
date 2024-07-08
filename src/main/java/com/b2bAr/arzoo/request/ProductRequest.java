package com.b2bAr.arzoo.request;

import com.b2bAr.arzoo.entity.ProductCategories;
import com.b2bAr.arzoo.entity.ProductVariations;
import com.b2bAr.arzoo.entity.SubCategoryEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
public class ProductRequest {

    private int productId;
    private String productName;
    private ProductCategories productCategories;
    private SubCategoryEntity subCategoryEntity;
    private String description;
    private String image_url;
    private double price;
    private boolean availability_status;
    private int stock;

}
