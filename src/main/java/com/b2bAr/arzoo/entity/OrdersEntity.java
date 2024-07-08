package com.b2bAr.arzoo.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.tomcat.util.buf.C2BConverter;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class OrdersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "productId")
    private ProductsEntity productEntity;


    @Column(columnDefinition = "TIMESTAMP")
    private Date orderDate;

    private List<Integer> quantity;


    private String status;
}
