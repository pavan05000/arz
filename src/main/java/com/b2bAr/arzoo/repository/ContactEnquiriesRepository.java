package com.b2bAr.arzoo.repository;

import com.b2bAr.arzoo.entity.ContactEnquiries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactEnquiriesRepository extends JpaRepository<ContactEnquiries, Integer> {

}
