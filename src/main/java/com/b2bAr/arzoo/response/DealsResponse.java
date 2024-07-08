package com.b2bAr.arzoo.response;

import lombok.Data;

import java.util.Date;

@Data
public class DealsResponse {

    private int dealId;
    private String dealName;
    private String description;
    private Date startDate;
    private Date endDate;
    private double discountPercentage;


}
