package com.gowtham.project01.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponseModel {
    private boolean validToken;
    private String message;
}
