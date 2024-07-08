package com.b2bAr.arzoo.repository;

import com.b2bAr.arzoo.entity.BannersEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends JpaRepository<BannersEntity, Integer> {

}
