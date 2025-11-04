package com.gowtham.project01.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gowtham.project01.models.PermissionModel;

@Repository
public interface PermissionRepo extends JpaRepository<PermissionModel, Integer> {
    // PermissionModel findList<PermissionModel> findByName(String name);
    PermissionModel findByName(String name);
}
