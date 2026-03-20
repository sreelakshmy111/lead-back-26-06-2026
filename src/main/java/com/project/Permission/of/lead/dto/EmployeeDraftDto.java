package com.project.Permission.of.lead.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDraftDto {

    private Long id;

    private String firstName;
    private String middleName;
    private String lastName;
    private String empId;
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
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private Long addressId;
    private String password;
    private String tower;



}
