package com.b2bAr.arzoo.response;

import lombok.Data;

@Data
public class BannerResponse {

    private int bannerId;

    private String bannerName;
    private String image_url;
    private boolean displayStatus;
}
