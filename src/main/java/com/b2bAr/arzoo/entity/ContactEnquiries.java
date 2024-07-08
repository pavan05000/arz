package com.b2bAr.arzoo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "contactEnquiries")
public class ContactEnquiries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int enquiryId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;

    @Column(columnDefinition = "TIMESTAMP")
    private Date enquiryDate;

    private String enquiryMessage;

    private String responseMessage;


}
