package com.b2bAr.arzoo.request;

import lombok.Data;

@Data
public class CategoryUpdateRequest {
    private String categoryName;
    private String description;
}
