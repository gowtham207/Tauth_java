package com.gowtham.project01.Schema;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ApiResponseModel<T> {

    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    // Constructor
    public ApiResponseModel(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now(); // auto timestamp
    }

    // Optional no-args constructor if needed by Jackson
    public ApiResponseModel() {
        this.timestamp = LocalDateTime.now();
    }
}
