package com.b2bAr.arzoo.repository;

import com.b2bAr.arzoo.entity.DealsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DealsRepository extends JpaRepository<DealsEntity, Integer> {


    @Query("select u from DealsEntity  u where u.dealName = :dealName")
    Optional<DealsEntity> findDealName(String dealName);
}
