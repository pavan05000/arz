package com.b2bAr.arzoo.request;

import lombok.Data;

import java.util.Date;

@Data
public class ContactEnquiriesRequest {

  //  private int enquiryId;

    //private UserResponse userEntity;

    private Date enquiryDate;

    private String enquiryMessage;

    private String responseMessage;
}
