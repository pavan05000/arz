package com.b2bAr.arzoo.repository;

import com.b2bAr.arzoo.entity.SubCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategoryEntity, Integer> {

//    @Query("SELECT u FROM SubCategoryEntity WHERE u.productCategories =:productCategories")
//    Optional<SubCategoryEntity> findbyId(int productCategories);
}
