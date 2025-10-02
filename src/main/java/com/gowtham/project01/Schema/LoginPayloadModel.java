package com.gowtham.project01.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginPayloadModel {
    private String username;
    private String password;
}
