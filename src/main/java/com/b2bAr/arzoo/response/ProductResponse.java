package com.b2bAr.arzoo.response;

import com.b2bAr.arzoo.entity.ProductCategories;
import com.b2bAr.arzoo.entity.ProductVariations;
import com.b2bAr.arzoo.entity.SubCategoryEntity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {
    private int productId;
    private String productName;
    private String description;
    private double price;
    private boolean availability_status;
    private String image_url;
    private int stock;
    private CategoryResponse productCategories;
    private List<ProductVariationResponse> productVariations;

    // private SubCategoryResponse subCategoryEntity;
}
