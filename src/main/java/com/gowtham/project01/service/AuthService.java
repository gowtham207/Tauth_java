package com.gowtham.project01.service;

import com.gowtham.project01.Schema.ApiResponseModel;
import com.gowtham.project01.Schema.LoginResponseModel;
import com.gowtham.project01.Schema.TokenModel;
import com.gowtham.project01.configuration.PasswordConfig;
import com.gowtham.project01.models.UserActivityModel;
import com.gowtham.project01.models.UserModel;
import com.gowtham.project01.repo.UserActivityRepo;
import com.gowtham.project01.repo.UserRepo;
import com.gowtham.project01.utils.JWTUtils;
import com.gowtham.project01.utils.UserActivityLogUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {

        @Autowired
        private UserRepo userRepo;

        @Autowired
        private UserActivityRepo userActivityRepo;

        // token encryption and decryption package
        @Autowired
        private JWTUtils jwtUtils;

        // password encryption and decryption package
        @Autowired
        private PasswordConfig passwordConfig;

        public boolean isUserVerified(String token) {
                TokenModel data = jwtUtils.GetUsernameFromToken(token);
                String username = data.getSubject().split("//")[0];
                log.info("This is an info log " + username + "  " + data.getId());
                UserModel user = userRepo.findByUsernameAndId(username, java.util.UUID.fromString(data.getId()));
                // UserModel user = userRepo.findByUsername(username);
                if (user == null) {
                        return false;
                }
                if (!user.getIsVerified()) {
                        return false;
                }

                return true;
        }

        public ResponseEntity<ApiResponseModel<LoginResponseModel>> loginUserService(
                        HttpServletRequest req,
                        String username,
                        String password) {
                UserModel LoginUser = userRepo.findByUsername(username);
                if (LoginUser == null) {
                        return ResponseEntity
                                        .status(403)
                                        .body(
                                                        new ApiResponseModel<>(false, "Username or password incorrect",
                                                                        null));
                }

                UserActivityModel log = UserActivityLogUtils.createUserActivityLog(
                                req,
                                LoginUser.getUserId(),
                                "User logged in");

                if (passwordConfig
                                .PasswordEncoder()
                                .matches(password, LoginUser.getPassword())) {

                        if (!LoginUser.getIsVerified()) {
                                log.setActivity("Unverified user login attempt");
                                userActivityRepo.save(log);
                                return ResponseEntity
                                                .status(HttpStatus.FORBIDDEN)
                                                .body(new ApiResponseModel<>(false, "User is not active", null));
                        }

                        String accessToken = jwtUtils.GetAccessToken(
                                        LoginUser.getUsername(),
                                        LoginUser.getUserId());
                        String refreshToken = jwtUtils.GetRefreshToken(
                                        LoginUser.getUsername(),
                                        LoginUser.getUserId());
                        long expiresIn = jwtUtils.GetExpirationTime();
                        userRepo.updateLastLoginTime(LoginUser.getUserId());
                        LoginResponseModel response = new LoginResponseModel(
                                        accessToken,
                                        "bearer",
                                        expiresIn,
                                        refreshToken);
                        userActivityRepo.save(log);
                        return ResponseEntity
                                        .ok()
                                        .body(new ApiResponseModel<>(true, "Login successful", response));
                }
                log.setActivity("Invalid login attempt(Password incorrect)");
                userActivityRepo.save(log);
                return ResponseEntity
                                .status(HttpStatus.FORBIDDEN)
                                .body(
                                                new ApiResponseModel<>(false, "Username or password incorrect", null));
        }
}
