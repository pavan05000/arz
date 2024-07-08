package com.b2bAr.arzoo.request;

import lombok.Data;

@Data
public class CategoryRequest {
    private int categoryId;
    private String categoryName;
    private String description;
}
