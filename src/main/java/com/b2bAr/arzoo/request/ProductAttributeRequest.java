package com.b2bAr.arzoo.request;

import lombok.Data;

@Data


public class ProductAttributeRequest {

    private int attributeId;

    private String attributeName;
    private String attributeType;
}
