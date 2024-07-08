package com.b2bAr.arzoo.response;

import com.b2bAr.arzoo.entity.ProductsEntity;
import lombok.Data;

@Data
public class CartResponse {

    private int cartId;
    private ProductResponse2 productsEntity;
    private int cartCount;

}
