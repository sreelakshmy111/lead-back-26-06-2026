package com.project.Permission.of.lead.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonalManagementDto {

    private Long id;
    private String empId;
    private String name;
    private String gender;
    private LocalDate dob;
    private String isdCode;
    private String phone;
    private String email;
    private String qualification;
    private String experience;
    private String eid;
    private String buid;
    private List<String> territoryId= new ArrayList<>();
    private boolean active;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
    private Long addressId;



}
