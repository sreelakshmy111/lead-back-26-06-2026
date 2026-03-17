package com.project.Permission.of.lead.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_contact")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String custId;

    private String first_name;

    private String middle_name;

    private String last_name;


    private String gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date_of_birth;

    private Long address_id;

    private String isd_code;

    private  String phone;

    private String email;

    private String eid;

    private String buid;

    private boolean is_active;

    private LocalDateTime created_at;

    private Long created_by;

    private LocalDateTime updated_at;

    private Long updated_by;


}
