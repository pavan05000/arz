package com.b2bAr.arzoo.request;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;

@Data
public class UserRequest {
    private int userId;

    private String userName;
    private String email;
    private String password;
    private String role;

    private Date registerDate;
    private Date loginDate;
    private String address;
    private String phoneNo;

    private boolean activeStatus;
}
