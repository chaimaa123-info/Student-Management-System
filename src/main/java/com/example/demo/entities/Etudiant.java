package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;
import jakarta.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;


//déclare que ce Etudiant n est pas juste un classe c'est un entity = table dans database
@Entity
@Getter
@Setter 
@AllArgsConstructor
public class Etudiant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "nom", nullable = false, length =100)
    private String nom;

    @Column(name = "prenom", nullable = false, length =100)
    private String prenom;
     
    @Column(name = "age", nullable = false)
    private int age;
    
    
    @Column(name = "email", nullable = false, unique = true)
    private String email;



    //POUR SEND EMAIL INVITATION FOR REGISTRATION
    @Column(name = "is_verified", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isVerified;

    @Column(name = "account_creation_token")
    private String accountCreationToken;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) //FetchType.EAGER = RENVOIE TOUS QUI EST DANS L OBJET AVEC ID name + departmentId
    @JoinColumn(name = "department_id", nullable = false)
    @JsonProperty("department_id") //POUR que affiche et change dans postman "department_id"par  "department"  (pour que le client comprend )
    private Department department;
    
   
    
    // Constructeur par défaut (nécessaire pour Spring)
    public Etudiant() {}
    
    // Constructeur avec paramètres
    public Etudiant(Long id, String nom, String prenom, int age, String email,  Department department) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.email=email;
        this.department=department;
    }
    
    // Getters et Setters (indispensables pour Spring)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }


    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    //SI je veux juste le client recupère DepartmentID pas les autres  infos de object
    public Long getDepartment() {
         return department.getDepartmentId() ; }

    public void setDepartment(Department department) { this.department = department; }

  

 
}