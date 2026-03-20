package com.project.Permission.of.lead.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table


public class Enterprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(length = 255)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(length = 255)
    private String industry;

    @Column(name = "legal_form_enum")
    private Integer legalFormEnum;



    @Column(name = "address_id")
    private Long addressId;

    @Column(length = 255,name = "contact_email")
    private String contactEmail;

    @Column(length = 4,name = "ids_code")
    private String isdCode;


    @Column(length = 50,name = "contact_phone")
    private String contactPhone;


    @Column(name = "active")
    private boolean active;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();


    @Column(name = "created_by_user_id")
    private String createdBy;


    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @Column(name = "updated_by_user_id")
    private String updatedBy;

    @Column(name = "eid")
    private String eid;
//
//    @Column(length = 150)
//    private String eid;
//    @Column(length = 500)
//    private String address;


//    @OneToMany(mappedBy = "enterprise", cascade = CascadeType.ALL)
//    private Set<BussinessEnterprise> businessUnits;



    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
