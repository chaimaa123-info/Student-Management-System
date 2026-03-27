package com.example.demo.abstarct;

import com.example.demo.entities.Etudiant;
import com.example.demo.DTOs.EtudiantCreate;
import com.example.demo.DTOs.EtudiantUpdate;

//import java.util.List;

import org.springframework.data.domain.Page;

public interface EtudiantService {
    Page<Etudiant> getAllEtudiants(int page, int size);
    Etudiant getEtudiantById(Long id);
    Etudiant createEtudiant(EtudiantCreate etudiantCreate);
    Etudiant updateEtudiant(Long id, EtudiantUpdate etudiantDetails);
    boolean deleteEtudiant(Long id);

}