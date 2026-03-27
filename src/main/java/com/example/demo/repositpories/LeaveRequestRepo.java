package com.example.demo.repositpories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entities.LeaveRequest;
@Repository
public interface LeaveRequestRepo extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByEtudiantId(Long id);
}
