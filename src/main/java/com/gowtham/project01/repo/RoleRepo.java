package com.gowtham.project01.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gowtham.project01.models.RoleModel;

@Repository
public interface RoleRepo extends JpaRepository<RoleModel, Integer> {
    RoleModel findByName(String name);
}
