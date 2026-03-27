package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.demo.DTOs.LeaveRequestCreate;
import com.example.demo.abstarct.LeaveRequestService;

import com.example.demo.entities.Etudiant;
import com.example.demo.entities.LeaveRequest;
import com.example.demo.exception.CustomResponseException;
import com.example.demo.repositpories.EtudiantRepo;
import com.example.demo.repositpories.LeaveRequestRepo;

@Service
public class LeaveRequestImpl implements LeaveRequestService {

    @Autowired
    private EtudiantRepo etudiantRepo;

    @Autowired
    private LeaveRequestRepo leaveRequestRepo;

    @Override
    @PreAuthorize("@securityUtils.isOwner(#id)")
    public LeaveRequest createRequest(LeaveRequestCreate leaveRequestCreate, Long id) {
        
        Etudiant etudiant =  etudiantRepo.findById(id) 

                .orElseThrow(() -> CustomResponseException.ResourceNotFound(
                    "etudiant avec l'id " + id + " non trouvé"
                ));

        LeaveRequest leaveRequest = new LeaveRequest();

        leaveRequest.setStatus("Pending");
        leaveRequest.setStartDate(leaveRequestCreate.startDate());
        leaveRequest.setEndDate(leaveRequestCreate.endDate());
        leaveRequest.setReason(leaveRequestCreate.reason());
        leaveRequest.setEtudiant(etudiant);

        //sauvegarde un LeaveRequest dans la base de données 
        leaveRequestRepo.save(leaveRequest);

        return leaveRequest;
        
    } 

    @Override
    @PreAuthorize("@securityUtils.isOwner(#id)")
    public List<LeaveRequest> getAllEtudiantsId(Long id) {
      return  leaveRequestRepo.findByEtudiantId( id);
         
        
    }

}
