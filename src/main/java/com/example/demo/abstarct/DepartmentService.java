package com.example.demo.abstarct;

import com.example.demo.entities.Department;

import java.util.List;

public interface DepartmentService {


    List<Department> getAllDepartments();
    Department getDepartmentById(Long departmentId);
    Department createDepartment(Department department);
    boolean deleteDepartment(Long departmentId);

}
