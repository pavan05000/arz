package com.b2bAr.arzoo.request;

import lombok.Data;

@Data
public class BannerRequest {

  //  private int bannerId;

    private String bannerName;
    private String image_url;
    private boolean displayStatus;
}
