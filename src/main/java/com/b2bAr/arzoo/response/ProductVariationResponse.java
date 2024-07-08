package com.b2bAr.arzoo.response;

import com.b2bAr.arzoo.entity.ProductAttribute;
import com.b2bAr.arzoo.entity.ProductsEntity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.util.List;

@Data
public class ProductVariationResponse {

    private int variationId;
    //    private ProductsEntity productsEntity;
    private ProductAttributeResponse2 productAttribute;
    private List<String> value;

}
