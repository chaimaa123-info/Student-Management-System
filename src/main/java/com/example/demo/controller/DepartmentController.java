package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.abstarct.DepartmentService;
import com.example.demo.entities.Department;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    
    @Autowired 
    // Le controller ne fait que recevoir les requêtes et déléguer au service
    private  DepartmentService departmentService;  // ← Le service dont j'ai besoin
    

    
    
    // 1️⃣ GET : Récupérer tous les departements
    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }
    
    // 2️⃣ GET : Récupérer une departement par son ID
    @GetMapping("/{departmentId}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long departmentId) {
        try {
            Department department = departmentService.getDepartmentById(departmentId);
            return new ResponseEntity<>(department, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // 3️⃣ POST : Ajouter un nouvel departement
    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department newDepartment = departmentService.createDepartment(department);
        return new ResponseEntity<>(newDepartment, HttpStatus.CREATED);
    }
    
    
    
    // 5️⃣ DELETE : Supprimer un departement
    @DeleteMapping("/{departmentId}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long departmentId) {
        boolean deleted = departmentService.deleteDepartment(departmentId);
        if (deleted) {
            return new ResponseEntity<>("Departement supprimé avec succès", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Departement non trouvé", HttpStatus.NOT_FOUND);
        }
    }
}

