package com.example.demo.abstarct;

import java.util.List;

import com.example.demo.DTOs.LeaveRequestCreate;
import com.example.demo.entities.LeaveRequest;

public interface LeaveRequestService {

    LeaveRequest createRequest(LeaveRequestCreate leaveRequest, Long id);

    List<LeaveRequest> getAllEtudiantsId(Long id);

    
}
