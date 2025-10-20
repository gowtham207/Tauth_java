package com.gowtham.project01.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gowtham.project01.models.AdminUserModel;
import java.util.List;

@Repository
public interface AdminUserRepo extends JpaRepository<AdminUserModel, UUID> {
    AdminUserModel findByEmail(String email);

    AdminUserModel findByAdminUuid(UUID adminUuid);

    AdminUserModel findByUsername(String username);
}