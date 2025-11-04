package com.gowtham.project01.configuration;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gowtham.project01.Schema.LoginResponseModel;
import com.gowtham.project01.utils.JWTUtils;

@Component
public class AuthUtils {

    @Autowired
    private JWTUtils jwtUtils;

    public LoginResponseModel loginResponse(String username, UUID id) {
        String accessToken = jwtUtils.GetAccessToken(username, id);
        String refreshToken = jwtUtils.GetRefreshToken(username, id);
        long expiresIn = jwtUtils.GetExpirationTime();

        return new LoginResponseModel(accessToken, "bearer", expiresIn, refreshToken);
    }
}
