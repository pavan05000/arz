package com.b2bAr.arzoo.request;

import com.b2bAr.arzoo.entity.ProductAttribute;
import com.b2bAr.arzoo.entity.ProductsEntity;
import lombok.Data;

import java.util.List;

@Data
public class ProductVariationRequest {

    private int variationId;
    private ProductsEntity productsEntity;
    private ProductAttribute productAttribute;
    private List<String> value;
}
