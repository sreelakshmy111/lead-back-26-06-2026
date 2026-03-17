package com.project.Permission.of.lead.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
//@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bussiness_unit")


public class BussinessUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bussiness_unit_name",length = 250)
    private String name;

    @Column(length = 100)
    private String description;

    @Column(length = 255)
    private String industry;

    @Column(name = "legal_form_enum")
    private Integer legalFormEnum;


    @Column(name = "address_id")
    private Long addressId;

    @Column (length = 255)
    private String contactEmail;

    @Column(length = 4)
    private String isdCode;

    @Column(length = 255)
    private String contactPhone;

    @Column(name = "is_active")
     private boolean active = true;

    private LocalDateTime createdAt = LocalDateTime.now();


    @Column(name = "create_by_id")
    private Long createdBy;

    private LocalDateTime updatedAt = LocalDateTime.now();


    @Column(name = "update_by_id")
    private Long updatedBy;


    @Column(name = "enterprise_id")
    private String enterpriseId;


    @Column(name = "buid")
    private String buid;


    public BussinessUnit(Long id, String name, String description, String industry, Integer legalFormEnum, Long addressId, String contactEmail, String isdCode, String contactPhone, boolean active, LocalDateTime createdAt, Long createdBy, LocalDateTime updatedAt, Long updatedBy, String enterpriseId, String buid) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.industry = industry;
        this.legalFormEnum = legalFormEnum;
        this.addressId = addressId;
        this.contactEmail = contactEmail;
        this.isdCode = isdCode;
        this.contactPhone = contactPhone;
        this.active = active;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.enterpriseId = enterpriseId;
        this.buid = buid;
    }


}
