package com.b2bAr.arzoo.repository;


import com.b2bAr.arzoo.entity.ProductVariations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariationRepository extends JpaRepository<ProductVariations, Integer> {

}
