package com.b2bAr.arzoo.response;

import com.b2bAr.arzoo.entity.ProductCategories;
import lombok.Data;

import java.util.List;

@Data
public class SubCategoryResponse {

    private int subCategoryId;

    private String subCategoryName;
    private CategoryResponse2 productCategories;
    private List<ProductResponse2> productsEntity;
}
