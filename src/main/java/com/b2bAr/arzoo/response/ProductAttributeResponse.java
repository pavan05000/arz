package com.b2bAr.arzoo.response;

import com.b2bAr.arzoo.entity.ProductVariations;
import lombok.Data;

import java.util.List;

@Data
public class ProductAttributeResponse {

    private int attributeId;

    private String attributeName;
    private String attributeType;
    private List<ProductVariationResponse3> productVariations;
}
