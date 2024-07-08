package com.b2bAr.arzoo.response;

import com.b2bAr.arzoo.entity.UserEntity;
import lombok.Data;

import java.util.Date;

@Data
public class ContactEnquiriesResponse {

    private int enquiryId;

    private UserResponse userEntity;

    private Date enquiryDate;

    private String enquiryMessage;

    private String responseMessage;
}
