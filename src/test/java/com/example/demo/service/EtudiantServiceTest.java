package com.example.demo.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.DTOs.EtudiantCreate;
import com.example.demo.entities.Department;
import com.example.demo.entities.Etudiant;
import com.example.demo.exception.CustomResponseException;
import com.example.demo.repositpories.DepartmentRepo;
import com.example.demo.repositpories.EtudiantRepo;
import com.example.demo.services.EmailService;
import com.example.demo.services.EtudiantServiceImpl;

import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EtudiantServiceTest {
  @Mock
  private DepartmentRepo departmentRepo;

  @Mock
  private EtudiantRepo etudiantRepo;

  @Mock
  private EmailService emailService;

  @InjectMocks
  private EtudiantServiceImpl etudiantService;

  private Department testDepartment;
  private Long departmentId;
  private EtudiantCreate etudiantCreate;

  @BeforeEach
  void setUp() {
    departmentId = 1L; //ID du département qui sera utilisé dans plusieurs tests
    testDepartment = new Department(departmentId, "IT");
    etudiantCreate = new EtudiantCreate(
            null,
            "Dupont",      // nom
            "Jean",        // prenom
            25,            // age
            "jean.dupont@example.com",  // email
            departmentId   // departmentId
        );
    }

  @Test 
  @DisplayName("createOne should create etudiant successfully")
  void createOne_shouldCreateEtudiantSuccessfully() {
    // ARRANGE
    when(departmentRepo.findById(departmentId))
        .thenReturn(Optional.of(testDepartment));

    when(etudiantRepo.save(any(Etudiant.class)))
        .thenAnswer(i -> i.getArgument(0));

    doNothing().when(emailService)
        .sendAccountCreationEmail(
            any(String.class), any(String.class));

    // ACT
    Etudiant result = etudiantService.createEtudiant(etudiantCreate);

    // ASSERT
    assertEquals("Dupont", result.getNom());

    // Verify
    verify(emailService, times(1))
        .sendAccountCreationEmail(eq("jean.dupont@example.com"), any(String.class));
  }

  @Test
  @DisplayName("createOne should throw exception when department not found")
  void createOne_shouldThrowExceptionWhenDepNotFound() {
    when(departmentRepo.findById(departmentId)).thenReturn(Optional.empty());

    CustomResponseException exception = assertThrows(
        CustomResponseException.class,
        () -> etudiantService.createEtudiant(etudiantCreate)
    );

    assertTrue(exception.getMessage().contains("Department with id " + departmentId + " not found"));

    verify(emailService, never())
        .sendAccountCreationEmail(any(String.class), any(String.class));
    verify(etudiantRepo, never())
        .save(any(Etudiant.class));
  }
}