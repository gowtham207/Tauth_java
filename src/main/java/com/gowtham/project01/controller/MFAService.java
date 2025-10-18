package com.gowtham.project01.controller;

import org.springframework.stereotype.Service;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

@Service
public class MFAService {
    private final GoogleAuthenticator gAuth = new GoogleAuthenticator();

    public String generateSecretKey() {
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        return key.getKey();
    }

    public boolean verifyCode(String secretKey, int code) {
        return gAuth.authorize(secretKey, code);
    }

    public String getOtpAuthURL(String appName, String username, String secret) {
        return String.format(
                "otpauth://totp/%s:%s?secret=%s&issuer=%s",
                appName, username, secret, appName);
    }
}
