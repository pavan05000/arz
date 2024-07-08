package com.b2bAr.arzoo.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderResponse {

    private int orderId;
    private int noOfOrders;
    private UserResponse2 userEntity;
    private ProductResponse2 productEntity;

    private Date orderDate;
    private List<Integer> quantity;


    private String status;
}
