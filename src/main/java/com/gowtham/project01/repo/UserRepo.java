package com.gowtham.project01.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gowtham.project01.models.UserModel;

@Repository
public interface UserRepo extends JpaRepository<UserModel, UUID> {
    UserModel findByEmail(String email);

    UserModel findByUserId(UUID userId);

    UserModel findByUsername(String username);
}
