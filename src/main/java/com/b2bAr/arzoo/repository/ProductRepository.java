package com.b2bAr.arzoo.repository;

import com.b2bAr.arzoo.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductsEntity, Integer> {

}
