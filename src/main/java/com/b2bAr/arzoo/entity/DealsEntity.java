package com.b2bAr.arzoo.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "deals")
public class DealsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dealId;

    private String dealName;


    private String description;

    @Column(columnDefinition = "TIMESTAMP")
    private Date startDate;
    @Column(columnDefinition = "TIMESTAMP")
    private Date endDate;
    private double discountPercentage;

}
