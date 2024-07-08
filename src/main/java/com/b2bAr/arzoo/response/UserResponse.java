package com.b2bAr.arzoo.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserResponse {
    private int userId;

    private String userName;
    private String email;
    private String password;
    private String role;

    private Date registerDate;
    private Date loginDate;
    private String address;
    private String phoneNo;
    private List<OrderResponse2> ordersEntity;
    private boolean activeStatus;
}
