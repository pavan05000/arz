package com.b2bAr.arzoo.response;

import lombok.Data;

@Data
public class ResultLoginResponse {

    private String result;
    private int userId;
    private String userName;
}
