package com.example.demo.DTOs;

public record EtudiantCreate(
    //PAS DE PRIVATE DANS RECORD
    Long id,
    String nom,
    String prenom,
    int age,
    String email,
    Long departmentId
) {}