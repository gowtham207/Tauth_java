package com.gowtham.project01.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupMFARequestModel {
    private String userId;
    private String mfaCode;
}
