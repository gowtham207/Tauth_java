package com.gowtham.project01.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gowtham.project01.models.UserModel;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepo extends JpaRepository<UserModel, UUID> {
    UserModel findByEmail(String email);

    UserModel findByUserId(UUID userId);

    UserModel findByUsername(String username);

    @Modifying
    @Transactional
    @Query("UPDATE UserModel u SET u.lastLoginTime = CURRENT_TIMESTAMP WHERE u.userId = :userId")
    void updateLastLoginTime(@Param("userId") UUID userId);
}
