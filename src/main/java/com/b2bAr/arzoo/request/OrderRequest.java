package com.b2bAr.arzoo.request;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    private List<Integer> quantity;

}
