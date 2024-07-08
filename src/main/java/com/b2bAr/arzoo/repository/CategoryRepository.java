package com.b2bAr.arzoo.repository;

import com.b2bAr.arzoo.entity.ProductCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<ProductCategories, Integer> {

    @Query("SELECT DISTINCT c FROM ProductCategories c LEFT JOIN FETCH c.subCategories")
    List<ProductCategories> findAllCategoriesWithSubcategories();

    @Query("select u from ProductCategories u where u.categoryName = :categoryName")
    Optional<ProductCategories> findCategoryName(String categoryName);
}
