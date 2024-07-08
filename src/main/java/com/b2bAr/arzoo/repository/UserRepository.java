package com.b2bAr.arzoo.repository;

import com.b2bAr.arzoo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    //@Query("select u from UserEntity u where u.email = :email )

    @Query("select u from UserEntity u where u.email = :email and u.password = :password")
    Optional<UserEntity> findemailPassword(String email, String password);

    @Query("select u from UserEntity u where u.email = :email")
    Optional<UserEntity> findemail(String email);
    @Query("select u from UserEntity u where u.userName = :userName")
    Optional<UserEntity> findUserName(String userName);


//    @Query("SELECT MAX(d.userId) FROM EmailEntity d")
//    String highId();
}
