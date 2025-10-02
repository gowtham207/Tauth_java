package com.gowtham.project01.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseModel {

    private String AccessToken;
    private String tokenType = "Bearer";
    private Long expiresIn; // in seconds
    private String refreshToken;
}
