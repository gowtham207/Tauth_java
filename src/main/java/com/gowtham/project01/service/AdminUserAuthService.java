package com.gowtham.project01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gowtham.project01.Schema.ApiResponseModel;
import com.gowtham.project01.Schema.LoginPayloadModel;
import com.gowtham.project01.Schema.LoginResponseModel;
import com.gowtham.project01.configuration.PasswordConfig;
import com.gowtham.project01.models.AdminUserModel;
import com.gowtham.project01.repo.AdminUserRepo;
import com.gowtham.project01.utils.JWTUtils;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AdminUserAuthService {

    @Autowired
    private AdminUserRepo adminUserRepo;

    @Autowired
    private PasswordConfig passwordConfig;

    @Autowired
    private JWTUtils jwtUtils;

    public ResponseEntity<ApiResponseModel<?>> AdminLogin(HttpServletRequest req, LoginPayloadModel cred) {
        AdminUserModel user = adminUserRepo.findByEmail(cred.getEmail());
        if (user == null) {
            return ResponseEntity
                    .status(401)
                    .body(new ApiResponseModel<>(false, "Invalid email or password", null));
        }
        boolean isPasswordMatch = passwordConfig.PasswordEncoder().matches(cred.getPassword(), user.getPassword());
        if (!isPasswordMatch) {
            return ResponseEntity
                    .status(401)
                    .body(new ApiResponseModel<>(false, "Invalid email or password", null));
        }
        if (user.getMfaEnabled()) {
            String returnUrl = req.getRequestURI().toString() + "/verify?user_id=" + user.getAdminUuid();
            return ResponseEntity
                    .ok()
                    .body(new ApiResponseModel<>(true, "MFA required", returnUrl));
        }
        LoginResponseModel resp = new LoginResponseModel(
                jwtUtils.GetAccessToken(user.getEmail(), user.getAdminUuid()), "Bearer", jwtUtils.GetExpirationTime(),
                jwtUtils.GetRefreshToken(user.getEmail(), user.getAdminUuid()));
        return ResponseEntity.ok().body(new ApiResponseModel<LoginResponseModel>(true, "Login successful", resp));
    }

    public ResponseEntity<ApiResponseModel<String>> AdminSignup(AdminUserModel user) {
        AdminUserModel existingUser = adminUserRepo.findByEmail(user.getEmail());
        if (existingUser != null) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponseModel<>(false, "Admin user already exists", null));
        }
        user.setPassword(passwordConfig.PasswordEncoder().encode(user.getPassword()));
        adminUserRepo.save(user);
        return ResponseEntity
                .ok()
                .body(new ApiResponseModel<>(true, "Admin user registered successfully", null));
    }

}
