package com.b2bAr.arzoo.response;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserResponse3 {
    private int userId;

    private String userName;
    private String email;
    private String password;
    private String role;

    private Date registerDate;
    private String address;
    private String phoneNo;
    private List<CartResponse> cartEntity;

}
