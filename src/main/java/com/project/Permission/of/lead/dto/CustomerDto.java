package com.project.Permission.of.lead.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDto {
    private Long id;
    private String custId;
    private String first_name;
    private String middle_name;
    private String last_name;
    private String gender;
    private LocalDate date_of_birth;
    private Long address_id;
    private String isd_code;
    private String phone;
    private String email;
    private String eid;
    private String buid;
    private boolean is_active;
    private LocalDateTime created_at;
    private String created_by;
    private LocalDateTime updated_at;
    private String updated_by;


}
