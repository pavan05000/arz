package com.b2bAr.arzoo.response;

import lombok.Data;

import java.util.List;

@Data
public class ProductVariationResponse2 {
    private int variationId;
    private ProductResponse2 productsEntity;
    //    private ProductAttributeResponse2 productAttribute;
    private List<String> value;
}
