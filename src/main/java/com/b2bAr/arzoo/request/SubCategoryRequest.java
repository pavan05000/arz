package com.b2bAr.arzoo.request;

import lombok.Data;

@Data
public class SubCategoryRequest {

    private int subCategoryId;

    private String subCategoryName;
    private int categoryId;
}
