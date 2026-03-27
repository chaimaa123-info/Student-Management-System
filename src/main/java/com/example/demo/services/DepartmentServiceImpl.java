package com.example.demo.services;

import com.example.demo.abstarct.DepartmentService;
import com.example.demo.entities.Department;
import com.example.demo.exception.CustomResponseException;
import com.example.demo.repositpories.DepartmentRepo;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepo departmentRepo;

    @Override
    public Department createDepartment(Department department) {
    
        return  departmentRepo.save(department);
    }

    @Override
    public boolean deleteDepartment(Long departmentId) {
        
        Optional<Department> department = departmentRepo.findById(departmentId);
    
    if (department.isPresent()) {
        departmentRepo.deleteById(departmentId);  
        return true;
    }
    return false;
}
    

    @Override
    public List<Department> getAllDepartments() {
        
        return departmentRepo.findAll();
    }

    @Override
    public Department getDepartmentById(Long departmentId) {
        
        return departmentRepo.findById(departmentId)

                .orElseThrow(() -> CustomResponseException.ResourceNotFound(
                    "Department avec l'id " + departmentId + " non trouvé"
                ));
    }
    
    }


