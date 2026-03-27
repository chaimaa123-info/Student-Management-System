package com.example.demo.repositpories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entities.Department;


public interface DepartmentRepo extends JpaRepository<Department, Long> {

    
}
