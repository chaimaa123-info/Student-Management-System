package com.example.demo.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.abstarct.EtudiantService;
import com.example.demo.abstarct.LeaveRequestService;
import com.example.demo.config.JwtHelper;
import com.example.demo.entities.Department;
import com.example.demo.entities.Etudiant;


import java.util.List;


import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EtudiantController.class)
public class EtudiantControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private JwtHelper jwtHelper;

  @MockitoBean
  private EtudiantService etudiantService;

  @MockitoBean
  private LeaveRequestService leaveRequestService;

  @Test
  void shouldReturnAllEtudiants() throws Exception {
    // Arrange
     Department department = new Department(1L, "IT");
        // ✅ Création de l'étudiant avec les bons champs
        Etudiant etudiant = new Etudiant();
        etudiant.setId(1L);
        etudiant.setNom("John");
        etudiant.setPrenom("Doe");
        etudiant.setAge(24);
        etudiant.setEmail("john@gmail.com");
        etudiant.setDepartment(department);
        
        List<Etudiant> etudiants = List.of(etudiant);

    Page<Etudiant> etudiantPage = new PageImpl<>(etudiants);
    when(etudiantService.getAllEtudiants(0, 3)).thenReturn(etudiantPage);

    // Act & Assert
    mockMvc.perform(
            get("/etudiants").with(user("admin").roles("ADMIN")))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].nom").value("John"));
  }
}