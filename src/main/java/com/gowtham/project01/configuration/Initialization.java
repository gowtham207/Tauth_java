package com.gowtham.project01.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.gowtham.project01.models.PermissionModel;
import com.gowtham.project01.models.RoleModel;
import com.gowtham.project01.repo.PermissionRepo;
import com.gowtham.project01.repo.RoleRepo;

import jakarta.transaction.Transactional;

@Component
public class Initialization implements CommandLineRunner {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PermissionRepo permissionRepo;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        PermissionModel permission = permissionRepo.findByName("write/read:*");
        if (permission == null) {
            PermissionModel value = PermissionModel.builder().name("write/read:*").build();
            PermissionModel permissionVal = permissionRepo.save(value);

            RoleModel checkRole = roleRepo.findByName("admin");
            if (checkRole == null) {
                RoleModel role = RoleModel.builder().name("admin").permissionList(List.of(permissionVal.getId()))
                        .build();
                roleRepo.save(role);
            }
        }
    }
}
