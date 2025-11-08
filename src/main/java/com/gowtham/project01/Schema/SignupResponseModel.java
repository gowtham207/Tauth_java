package com.gowtham.project01.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupResponseModel {
    private String message;
    private String mfaUrl;
    private String user_id;
}
