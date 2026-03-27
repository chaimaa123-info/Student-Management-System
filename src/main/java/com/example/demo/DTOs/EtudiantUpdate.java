package com.example.demo.DTOs;

public class EtudiantUpdate {
    private String nom;
    private String prenom;
    
    // Constructeur par défaut (obligatoire pour Jackson)
    public EtudiantUpdate() {}
    
    // Constructeur avec paramètres
    public EtudiantUpdate(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }
    
    // Getters
    public String getNom() {
        return nom;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    // Setters
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}