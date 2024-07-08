package com.b2bAr.arzoo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "BannersTable")
public class BannersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bannerId;

    private String bannerName;
    private String image_url;
    private boolean displayStatus;

}
