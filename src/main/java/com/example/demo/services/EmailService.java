package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class EmailService {

  //faire injection au package de email
  @Autowired
  private JavaMailSender mailSender;

  @Value("${backend.origin}")
  private String ORIGIN;
  @Value("${spring.mail.username}")
  private String from;

  public void sendAccountCreationEmail(String to, String token) {
    String link = ORIGIN + "/auth/signup?token=" + token;
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(from);
    message.setTo(to);
    message.setSubject("Create Your Account!");
    message.setText("Hi! Please create your account using this link below: \n" + link);
    mailSender.send(message);
  }


 //Send Email (Token) Pour faire Reset_password
  public void sendPasswordRestEmail(String to, String token) {
    String link = ORIGIN + "/auth/reset-password?token=" + token;
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(from);
    message.setTo(to);
    message.setSubject("Rest Your Password!");
    message.setText("Hi! Click the link below to reset your password: \n" + link);
    mailSender.send(message);
  }

}
