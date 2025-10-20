package com.gowtham.project01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gowtham.project01.Schema.ApiResponseModel;
import com.gowtham.project01.Schema.LoginPayloadModel;
import com.gowtham.project01.models.UserModel;
import com.gowtham.project01.service.AdminUserAuthService;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// @Hidden
@RestController
@RequestMapping("/api/v1/admin")
public class AdminPanelController {

    @Autowired
    private AdminUserAuthService adminUserAuthService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponseModel<?>> postMethodName(HttpServletRequest req,
            @RequestBody LoginPayloadModel entity) {
        return adminUserAuthService.AdminLogin(req, entity);
    }

    @PostMapping("/signup")
    public String postMethodName(@RequestBody UserModel entity) {

        return "entity";
    }

}
