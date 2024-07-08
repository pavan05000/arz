package com.b2bAr.arzoo.request;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;

@Data
public class DealsRequest {



    private String dealName;
    private String description;
    private Date startDate;
    private Date endDate;
    private double discountPercentage;


}
