package com.example.demo.services;

import com.example.demo.abstarct.EtudiantService;
import com.example.demo.entities.Etudiant;
import com.example.demo.exception.CustomResponseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;  
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Department;

import java.util.Optional;
import java.util.Random;

import com.example.demo.DTOs.EtudiantCreate;
import com.example.demo.DTOs.EtudiantUpdate;
import com.example.demo.repositpories.DepartmentRepo;
import com.example.demo.repositpories.EtudiantRepo;

import jakarta.transaction.Transactional;


@Service
public class EtudiantServiceImpl implements EtudiantService {
    

    //Pour se connecter au Repository Etudiant
    @Autowired
    private EtudiantRepo etudiantRepo;
     
    
    //Pour se connecter au Repository Department pour prendre department_id
    @Autowired
    private DepartmentRepo departmentRepo;


    @Autowired
    private EmailService emailService;


    @Override
    public Page<Etudiant> getAllEtudiants(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return etudiantRepo.findAll(pageable);
    }
    

    
    @PreAuthorize("@securityUtils.isOwner(#id)")
    public Etudiant getEtudiantById(Long id) {
        Etudiant etudiant =  etudiantRepo.findById(id) 


                .orElseThrow(() -> CustomResponseException.ResourceNotFound(
                    "Etudiant avec l'id " + id + " non trouvé"
                ));
                
                return etudiant ;
    }
    
    
   @Transactional
    public Etudiant createEtudiant(EtudiantCreate etudiantCreate) {
        Etudiant etudiant = new Etudiant();

         Department department =  departmentRepo.findById(etudiantCreate.departmentId()) 

                .orElseThrow(() -> CustomResponseException.ResourceNotFound(
                    "Department with id " + etudiantCreate.departmentId() + " not found"
                ));

    //send Email au Nouveau Etudiant 
        String token =String.valueOf(Math.abs(new Random().nextLong()));  // Nombre long aléatoire 

        try{
        etudiant.setVerified(false);
        etudiant.setAccountCreationToken(token);

        
        etudiant.setNom(etudiantCreate.nom());
        etudiant.setPrenom(etudiantCreate.prenom());
        etudiant.setAge(etudiantCreate.age());
        etudiant.setEmail(etudiantCreate.email());
        etudiant.setDepartment(department);

        // sauvegarde un étudiant dans la base de données
         
        etudiantRepo.save(etudiant); 

        // → Exécute un INSERT INTO etudiant (nom, prenom, age) VALUES ('Dupont', 'Jean', 20)
        // → Récupère l'ID généré automatiquement et le met dans l'objet

        // On parle avec emailService pour  Send Email
        emailService.sendAccountCreationEmail(etudiant.getEmail(), token);
         return etudiant;
            
        } catch (Exception e){
            throw CustomResponseException.BadRequest("Etudiant creation failed");
        }
    } 
    


     @PreAuthorize("@securityUtils.isOwner(#id)")
    public Etudiant updateEtudiant(Long id, EtudiantUpdate etudiantDetails) {
        Etudiant etudiantToUpdate = etudiantRepo.findById(id)
                .orElseThrow(() -> CustomResponseException.ResourceNotFound(
                    "Etudiant avec l'id " + id + " non trouvé"
                ));
        
        etudiantToUpdate.setNom(etudiantDetails.getNom());
        etudiantToUpdate.setPrenom(etudiantDetails.getPrenom());
        
        etudiantRepo.save(etudiantToUpdate); 

        return etudiantToUpdate;
    }
    
  @Override
public boolean deleteEtudiant(Long id) {
    Optional<Etudiant> etudiant = etudiantRepo.findById(id);
    
    if (etudiant.isPresent()) {
        etudiantRepo.deleteById(id);  
        return true;
    }
    return false;
}
}