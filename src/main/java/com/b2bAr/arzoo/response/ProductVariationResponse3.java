package com.b2bAr.arzoo.response;

import lombok.Data;

import java.util.List;

@Data
public class ProductVariationResponse3 {
    //this is for attribute
    private int variationId;
    private ProductResponse2 productsEntity;
    private List<String> value;
}
