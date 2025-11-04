package com.gowtham.project01.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MFAPayload {
    private String mfa;
    private String uuid;
}
