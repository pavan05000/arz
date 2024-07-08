package com.b2bAr.arzoo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "userEntity")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private String userName;
    private String email;
    private String password;
    private String role;

    @Column(columnDefinition = "TIMESTAMP")
    private Date registerDate;

    @Column(columnDefinition = "TIMESTAMP")
    private Date loginDate;

    @Column(columnDefinition = "TIMESTAMP")
    private Date logOut;

    private String address;
    private String phoneNo;
    private boolean activeStatus;

    @OneToMany(mappedBy = "userEntity", cascade=CascadeType.ALL)
    private List<ContactEnquiries> contestEnquiries;

    @OneToMany(mappedBy = "userEntity", cascade=CascadeType.ALL)
    private List<OrdersEntity> ordersEntity;

    @OneToMany(mappedBy = "userEntity", cascade=CascadeType.ALL)
    private List<CartEntity> cartEntity;
}
