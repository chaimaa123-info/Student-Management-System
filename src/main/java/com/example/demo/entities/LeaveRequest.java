package com.example.demo.entities;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;

@Entity
@Getter
@Setter
@NoArgsConstructor                                                                                                                                              
@AllArgsConstructor

public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "start_date", nullable = false)
    private LocalDate startDate ;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate ;
     
    @Column(name = "reason", nullable = false)
    private String reason;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) 
    @JoinColumn(name = "etudiant_id", nullable = false) //on s apelle foreing key par "etudiant_id"
    private Etudiant etudiant;

    //on fait ca car on deja fait  "FetchType.LAZY " qui retourne juste id pas tous les infos de object
    public Long getEtudiant(){
        return etudiant.getId();
    }
}
