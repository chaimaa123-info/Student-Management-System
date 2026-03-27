package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTOs.LoginRequest;
import com.example.demo.DTOs.ResetPasswordRequest;
import com.example.demo.DTOs.SignupRequest;
import com.example.demo.services.AuthService;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(
        @RequestBody LoginRequest loginRequest
    ) {
        String token = authService.login(loginRequest);
        

        // Retourne simplement un message String
        return new ResponseEntity<>((token), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(
      @RequestBody SignupRequest signupRequest,
      @RequestParam String token
  ) { 

    authService.signup(signupRequest, token);
    return new ResponseEntity<>("Signed Up", HttpStatus.CREATED);
  }


  //Forgot password : envoyer username par methode de parameters
    @PostMapping("/forgot-password/{username}")
  public ResponseEntity<String> forgotPassword(@PathVariable String username) {

    authService.initiatePasswordRest(username);
    return new ResponseEntity<>("Password reset email sent!", HttpStatus.CREATED);
  }

  //Reset Password
    @PostMapping("/reset-password")
  public ResponseEntity<String> resetPassword(
      @RequestBody ResetPasswordRequest resetPasswordRequest
  ) {
    authService.resetPassword(resetPasswordRequest);
    return new ResponseEntity<>("Password reset successfully!", HttpStatus.CREATED);
  }
}