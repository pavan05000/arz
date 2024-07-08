package com.b2bAr.arzoo.request;

import com.b2bAr.arzoo.entity.ProductCategories;
import com.b2bAr.arzoo.entity.SubCategoryEntity;
import lombok.Data;

@Data
public class ProductUpdateRequest {

    private String productName;
    private String description;
    private String image_url;
    private double price;
    private boolean availability_status;
    private int stock;
}
