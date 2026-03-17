package com.project.Permission.of.lead.service;

import com.project.Permission.of.lead.dto.EmployeeDraftDto;
import com.project.Permission.of.lead.dto.RegisterResponseDto;
import com.project.Permission.of.lead.entity.Users;
import org.springframework.http.ResponseEntity;

public interface EmployeeDraftService {
    RegisterResponseDto createEmployeeAfterRegister(EmployeeDraftDto personalManagementDto);

    boolean checkEmployeeByEmail(String email);

    void moveEmployeeDraft(Long createdBy,String eid);
}
