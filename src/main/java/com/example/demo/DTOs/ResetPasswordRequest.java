package com.example.demo.DTOs;

public record ResetPasswordRequest(
     String token,
    String newPassword
) {

}
