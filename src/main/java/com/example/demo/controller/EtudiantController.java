package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entities.Etudiant;
import com.example.demo.entities.LeaveRequest;

import jakarta.validation.Valid;

import com.example.demo.DTOs.EtudiantCreate;
import com.example.demo.DTOs.EtudiantUpdate;
import com.example.demo.DTOs.LeaveRequestCreate;
import com.example.demo.DTOs.PaginatedResponse;
import com.example.demo.abstarct.EtudiantService;
import com.example.demo.abstarct.LeaveRequestService;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;  

@RestController
@RequestMapping("/etudiants")
public class EtudiantController {

    // Le controller ne fait que recevoir les requêtes et déléguer au service
    private  EtudiantService etudiantService;  // ← Le service dont j'ai besoin
    
    @Autowired
    private LeaveRequestService leaveRequestService;


    // Injection de dépendance du service
    public EtudiantController(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }
    
    // 1️⃣ GET : Récupérer tous les étudiants
    @GetMapping
    public ResponseEntity<PaginatedResponse<Etudiant>> getAllEtudiants(
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "3") int size,
      HttpServletRequest request

    ) {
        int zeroBasedPage = page - 1;
        Page<Etudiant> etudiants = etudiantService.getAllEtudiants(zeroBasedPage, size);

         String baseUrl = request.getRequestURL().toString();
         String nextUrl = etudiants.hasNext() ? String.format("%s?page=%d&size=%d", baseUrl, page + 1, size) : null;
         String prevUrl = etudiants.hasPrevious() ? String.format("%s?page=%d&size=%d", baseUrl, page - 1, size) : null;

        var paginatedResponse = new PaginatedResponse<Etudiant>(
        etudiants.getContent(),
        etudiants.getNumber(),
        etudiants.getTotalPages(),
        etudiants.getTotalElements(),
        etudiants.hasNext(),
        etudiants.hasPrevious(),
        nextUrl,
        prevUrl
    );

        return new ResponseEntity<>((paginatedResponse), HttpStatus.OK);
    }
    
    // 2️⃣ GET : Récupérer un étudiant par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Etudiant> getEtudiantById(@PathVariable Long id) {
        try {
            Etudiant etudiant = etudiantService.getEtudiantById(id);
            return new ResponseEntity<>(etudiant, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // 3️⃣ POST : Ajouter un nouvel étudiant
    @PostMapping
    public ResponseEntity<Etudiant> createEtudiant(@RequestBody EtudiantCreate etudiantCreate) {
        Etudiant newEtudiant = etudiantService.createEtudiant(etudiantCreate);
        return new ResponseEntity<>(newEtudiant, HttpStatus.CREATED);
    }
    
    // 4️⃣ PUT : Modifier un étudiant existant
    @PutMapping("/{id}")
    public ResponseEntity<Etudiant> updateEtudiant(@PathVariable Long id, @RequestBody EtudiantUpdate etudiantDetails) {
        try {
            Etudiant updatedEtudiant = etudiantService.updateEtudiant(id, etudiantDetails);
            return new ResponseEntity<>(updatedEtudiant, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // 5️⃣ DELETE : Supprimer un étudiant
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEtudiant(@PathVariable Long id) {
        boolean deleted = etudiantService.deleteEtudiant(id);
        if (deleted) {
            return new ResponseEntity<>("Étudiant supprimé avec succès", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Étudiant non trouvé", HttpStatus.NOT_FOUND);
        }
    }

  
    //integrer LeaveRequestController ici 
    @PostMapping("/{id}/leave-request")
    public ResponseEntity<LeaveRequest> leaveRequest(@Valid@RequestBody LeaveRequestCreate leaveRequest, 
                                                     @PathVariable Long id) {
    
    //On va parler maintenant au service  qu'on a créer 
    LeaveRequest newLeaveRequest = leaveRequestService.createRequest(leaveRequest,  id);                                             
        return new ResponseEntity<>(newLeaveRequest, HttpStatus.CREATED);
    }


    //recupère tous les congés qui prend id d'un etudiant
    @GetMapping("/{id}/leave-requests") //on ajoute s pour ne tromper pas avec leave-request de @PostMapping
    public ResponseEntity<List<LeaveRequest>> getLeaveRequestsByEtudiantId(
            @PathVariable Long id
    ){
        // Appel au service pour récupérer toutes les demandes de l'étudiant
        List<LeaveRequest> leaveRequests = leaveRequestService. getAllEtudiantsId(id);

         return new ResponseEntity<>( leaveRequests, HttpStatus.CREATED);
    }

}