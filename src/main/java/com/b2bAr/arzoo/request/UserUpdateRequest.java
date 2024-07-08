package com.b2bAr.arzoo.request;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String userName;
    private String email;
    private String password;
    private String role;
    private String address;
    private String phoneNo;
}
