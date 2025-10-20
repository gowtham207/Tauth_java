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
import com.gowtham.project01.Schema.SignupMFARequestModel;
import com.gowtham.project01.Schema.SignupResponseModel;
import com.gowtham.project01.Schema.TokenPayloadModel;
import com.gowtham.project01.Schema.TokenResponseModel;
import com.gowtham.project01.models.UserModel;
import com.gowtham.project01.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

        @Autowired
        private AuthService authService;

        @PostMapping("/authorizer")
        public ResponseEntity<ApiResponseModel<TokenResponseModel>> postMethodName(
                        @RequestBody TokenPayloadModel entity) {
                String token = entity.getToken();
                if (token == null || token.isEmpty() || !authService.isUserVerified(token)) {
                        return ResponseEntity
                                        .status(HttpStatus.UNAUTHORIZED)
                                        .body(new ApiResponseModel<>(false, "Invalid token",
                                                        new TokenResponseModel(false, "Invalid token")));
                }

                return ResponseEntity
                                .ok(new ApiResponseModel<>(true, "Success",
                                                new TokenResponseModel(true, "Valid token")));
        }

        @PostMapping("/login")
        public ResponseEntity<ApiResponseModel<String>> LoginUser(HttpServletRequest req,
                        @RequestBody LoginPayloadModel entity) {
                String email = entity.getEmail();
                String password = entity.getPassword();

                if (email != null && password != null) {
                        return authService.loginUserService(req, email, password);
                }
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body(new ApiResponseModel<>(false, "Email or password incorrect", null));
        }

        @PostMapping("/signup")
        public ResponseEntity<ApiResponseModel<SignupResponseModel>> CreateUser(HttpServletRequest req,
                        @RequestBody UserModel entity) {
                return authService.SignupUserService(req, entity);
        }

        @PostMapping("/signup/mfa")
        public ResponseEntity<ApiResponseModel<String>> postMethodName(HttpServletRequest req,
                        @RequestBody SignupMFARequestModel entity) {
                return authService.VerifySignupMFA(req, entity);
        }

}
