package com.gowtham.project01.service;

import com.gowtham.project01.Schema.ApiResponseModel;
import com.gowtham.project01.Schema.LoginResponseModel;
import com.gowtham.project01.Schema.MFAPayload;
import com.gowtham.project01.Schema.SignupMFARequestModel;
import com.gowtham.project01.Schema.SignupResponseModel;
import com.gowtham.project01.Schema.TokenModel;
import com.gowtham.project01.configuration.AuthUtils;
import com.gowtham.project01.configuration.PasswordConfig;
import com.gowtham.project01.models.UserActivityModel;
import com.gowtham.project01.models.UserModel;
import com.gowtham.project01.repo.UserActivityRepo;
import com.gowtham.project01.repo.UserRepo;
import com.gowtham.project01.utils.JWTUtils;
import com.gowtham.project01.utils.UserActivityLogUtils;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {

        // @Autowired
        // private UserActivityLogUtils userActivityLogUtils;

        @Autowired
        private UserRepo userRepo;

        @Autowired
        private UserActivityRepo userActivityRepo;

        // token encryption and decryption package
        @Autowired
        private JWTUtils jwtUtils;

        @Autowired
        private AuthUtils authUtils;

        // mfa service
        @Autowired
        private MFAService mfaService;

        // password encryption and decryption package
        @Autowired
        private PasswordConfig passwordConfig;

        public boolean isUserVerified(String token) {
                TokenModel data = jwtUtils.GetUsernameFromToken(token);
                String email = data.getSubject().split("//")[0];
                log.info("This is an info log " + email + "  " + data.getId());
                UserModel user = userRepo.findByUsernameAndId(email, java.util.UUID.fromString(data.getId()));
                if (user == null) {
                        return false;
                }
                if (!user.getIsVerified()) {
                        return false;
                }

                return true;
        }

        public ResponseEntity<ApiResponseModel<SignupResponseModel>> SignupUserService(HttpServletRequest req,
                        UserModel newUser) {
                // check if user already exists
                UserModel existingUser = userRepo.findByEmail(newUser.getEmail());
                if (existingUser != null) {
                        return ResponseEntity
                                        .status(HttpStatus.CONFLICT)
                                        .body(new ApiResponseModel<>(false, "email already exists",
                                                        null));
                }
                LocalDateTime dob = newUser.getDOB();
                int currentYear = LocalDate.now().getYear();

                if (dob.getYear() > currentYear) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                        .body(new ApiResponseModel<>(false, "Invalid dob was entered ", null));
                }

                // encrypt the password
                String encryptedPassword = passwordConfig
                                .PasswordEncoder()
                                .encode(newUser.getPassword());
                newUser.setIsVerified(true);
                newUser.setPassword(encryptedPassword);

                // create the mfa secret key
                String mfaSecString = mfaService.generateSecretKey();
                newUser.setMfaSecret(mfaSecString);

                // create the user
                UserModel savedUser = userRepo.save(newUser);
                String MFAUrl = mfaService.getOtpAuthURL("TauthJava",
                                savedUser.getEmail() + "@" + savedUser.getUserId(),
                                mfaSecString);
                SignupResponseModel responseModel = new SignupResponseModel(
                                "User registered successfully", MFAUrl);

                // logging the user activity
                UserActivityLogUtils.createUserActivityLog(req, savedUser.getUserId(),
                                "Signup new user");

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(new ApiResponseModel<>(true, "User registered successfully",
                                                responseModel));
        }

        public ResponseEntity<ApiResponseModel<?>> loginUserService(
                        HttpServletRequest req,
                        String email,
                        String password) {

                UserModel LoginUser = userRepo.findByEmail(email);
                if (LoginUser == null) {
                        return ResponseEntity
                                        .status(403)
                                        .body(
                                                        new ApiResponseModel<>(false, "email or password incorrect",
                                                                        null));
                }

                UserActivityModel logOut = UserActivityLogUtils.createUserActivityLog(
                                req,
                                LoginUser.getUserId(),
                                "User logged in");

                if (passwordConfig
                                .PasswordEncoder()
                                .matches(password, LoginUser.getPassword())) {

                        if (!LoginUser.getIsVerified()) {
                                logOut.setActivity("Unverified user login attempt");
                                userActivityRepo.save(logOut);
                                return ResponseEntity
                                                .status(HttpStatus.FORBIDDEN)
                                                .body(new ApiResponseModel<>(false, "User is not active", null));
                        }
                        userRepo.updateLastLoginTime(LoginUser.getUserId());
                        // userActivityRepo.save(logOut);

                        if (LoginUser.getMfaEnabled()) {
                                return ResponseEntity
                                                .ok()
                                                .body(new ApiResponseModel<>(true, "Login successful",
                                                                "Login Successful"));
                        }
                        LoginResponseModel resp = authUtils.loginResponse(LoginUser.getUsername(),
                                        LoginUser.getUserId());
                        return ResponseEntity
                                        .ok()
                                        .body(new ApiResponseModel<LoginResponseModel>(true, "Login successful", resp));
                }
                logOut.setActivity("Invalid login attempt(Password incorrect)");
                userActivityRepo.save(logOut);
                return ResponseEntity
                                .status(HttpStatus.FORBIDDEN)
                                .body(new ApiResponseModel<>(false, "email or password incorrect", null));
        }

        public ResponseEntity<ApiResponseModel<String>> VerifySignupMFA(
                        HttpServletRequest req,
                        SignupMFARequestModel entity) {
                // check the user exists
                UserModel user = userRepo.findById(java.util.UUID.fromString(entity.getUserId())).orElse(null);
                if (user == null) {
                        return ResponseEntity
                                        .status(HttpStatus.NOT_FOUND)
                                        .body(new ApiResponseModel<>(false, "User not found", null));
                }

                Boolean isOldCodeValid = mfaService.verifyCode(
                                user.getMfaSecret(),
                                Integer.parseInt(entity.getOldMfaCode()));

                Boolean isNewCodeValid = mfaService.verifyCode(
                                user.getMfaSecret(),
                                Integer.parseInt(entity.getNewMfaCode()));
                if (isOldCodeValid || isNewCodeValid) {
                        UserActivityLogUtils.createUserActivityLog(req, user.getUserId(), "MFA code verified");
                        return ResponseEntity
                                        .status(HttpStatus.ACCEPTED)
                                        .body(new ApiResponseModel<>(true,
                                                        "MFA code verified successfully", null));
                }
                UserActivityLogUtils.createUserActivityLog(req, user.getUserId(), "MFA code verification failed");
                return ResponseEntity
                                .status(HttpStatus.FORBIDDEN)
                                .body(new ApiResponseModel<>(false, "Invalid MFA code", null));
        }

        public ResponseEntity<ApiResponseModel<?>> LoginMFAService(HttpServletRequest req, MFAPayload payload) {
                UserModel user = userRepo.findById(java.util.UUID.fromString(payload.getUuid())).orElse(null);
                if (user == null) {
                        return ResponseEntity.status(401).body(new ApiResponseModel<>(
                                        false, "Unauthorized user", null));
                }
                Boolean verificationStatus = mfaService.verifyCode(
                                user.getMfaSecret(),
                                Integer.parseInt(payload.getMfa()));
                if (verificationStatus) {
                        UserActivityLogUtils.createUserActivityLog(req, user.getUserId(),
                                        "MFA code verification Successfully");
                        LoginResponseModel val = authUtils.loginResponse(user.getUsername(), user.getUserId());
                        return ResponseEntity.status(200)
                                        .body(new ApiResponseModel<LoginResponseModel>(true, "MFA login success", val));
                }
                UserActivityLogUtils.createUserActivityLog(req, user.getUserId(), "MFA code verification Failed");
                return ResponseEntity.status(200)
                                .body(new ApiResponseModel<String>(true, "MFA login success", ""));

        }

}
