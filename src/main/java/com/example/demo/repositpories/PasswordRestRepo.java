package com.example.demo.repositpories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.PasswordRestToken;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PasswordRestRepo extends JpaRepository<PasswordRestToken, UUID> {
  Optional<PasswordRestToken> findOneByToken(String token);
}