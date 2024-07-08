package com.b2bAr.arzoo.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderResponse2 {


    private int orderId;

    private ProductResponse2 productEntity;
    private Date orderDate;
    private String status;
    private List<Integer> quantity;

}
