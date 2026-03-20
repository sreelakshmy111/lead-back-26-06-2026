package com.project.Permission.of.lead.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="employee")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class PersonalManagement {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "first_name")
    private String firstName;


    @Column(name = "middle_name")
    private String middleName;


    @Column(name = "last_name")
    private String lastName;

    @Column(name = "emp_id")
    private String empId;


    @Column(name = "gender")
    private String gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_of_birth")
    private LocalDate dob;

    @Column(name = "ids_code")
    private String isdCode;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "qualification")
    private String qualification;

    @Column(name = "experience")
    private String experience;

    private String eid;

    private String buid;

    @Column(name = "territory_id")
    private List<String> territoryId =new ArrayList<>();

    @Column(name = "is_active")
    private boolean active;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "created_by")
    private String created_by;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @Column(name = "updated_by")
    private String updated_by;

    @Column(name = "address_id")
    private Long addressId;

}
