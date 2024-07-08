package com.b2bAr.arzoo.repository;

import com.b2bAr.arzoo.entity.OrdersEntity;
import jakarta.persistence.criteria.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository  extends JpaRepository<OrdersEntity, Integer> {
}
