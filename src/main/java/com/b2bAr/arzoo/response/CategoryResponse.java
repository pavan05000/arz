package com.b2bAr.arzoo.response;

import com.b2bAr.arzoo.entity.SubCategoryEntity;
import lombok.Data;

import java.util.List;


@Data
public class CategoryResponse {

    private int categoryId;
    private String categoryName;
    private String description;
    private List<SubCategoryResponse2> subCategories;

}
