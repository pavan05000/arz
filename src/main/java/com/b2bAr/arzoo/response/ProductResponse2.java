package com.b2bAr.arzoo.response;

import lombok.Data;

@Data
public class ProductResponse2 {

    private int productId;
    private String productName;
    private String image_url;
    private String description;
    private double price;
    private boolean availability_status;
    private int stock;
}
