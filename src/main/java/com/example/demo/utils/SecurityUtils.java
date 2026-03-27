package com.example.demo.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.demo.repositpories.UserAccountRepo;

@Component //car on veut  le faire injection 
public class SecurityUtils {

  @Autowired
  private UserAccountRepo userAccountRepo;

  public boolean isOwner(Long incomingEtudiantId) {
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    System.out.println(userDetails.getUsername());
    System.out.println("HERE " + incomingEtudiantId);


    return userAccountRepo.isOwner(userDetails.getUsername(), incomingEtudiantId);
  }
} 
