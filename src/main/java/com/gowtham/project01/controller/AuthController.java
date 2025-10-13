package com.gowtham.project01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gowtham.project01.Schema.ApiResponseModel;
import com.gowtham.project01.Schema.LoginPayloadModel;
import com.gowtham.project01.Schema.LoginResponseModel;
import com.gowtham.project01.configuration.PasswordConfig;
import com.gowtham.project01.models.UserModel;
import com.gowtham.project01.repo.UserRepo;
import com.gowtham.project01.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    // user Repo
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthService authService;

    // password encryption and decryption package
    @Autowired
    private PasswordConfig passwordConfig;

    @PostMapping("/login")
    public ResponseEntity<ApiResponseModel<LoginResponseModel>> LoginUser(HttpServletRequest req,
                    @RequestBody LoginPayloadModel entity) {
        String username = entity.getUsername();
        String password = entity.getPassword();

        if (username != null && password != null) {
                return authService.loginUserService(req, username, password);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiResponseModel<>(false, "Username or password incorrect", null));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseModel<String>> CreateUser(@RequestBody UserModel entity) {
        if (userRepo.findByEmail(entity.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponseModel<String>(false, "Email already exists",
                            "Please Login with different email"));
        }

        entity.setPassword(passwordConfig.PasswordEncoder().encode(entity.getPassword()));
        UserModel savedUser = userRepo.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseModel<String>(true, "User created successfully",
                        savedUser.getUserId().toString()));

    }
}
