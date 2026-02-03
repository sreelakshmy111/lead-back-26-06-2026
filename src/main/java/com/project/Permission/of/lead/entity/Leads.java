package com.project.Permission.of.lead.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "lms_lead")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Leads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lid;

    @Column(name = "lead_source")
    private String leadSource;

    @Column(name = "lead_for")
    private String leadFor;

    @Column(name = "product_group")
    private String productGroup;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "product_sku")
    private String productSku;

    @Column(name = "service_group")
    private String serviceGroup;

    @Column(name = "service_type")
    private String serviceType;

    @Column(name = "service_item")
    private String serviceItem;

    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "territory_id")
    private String territoryId;

    @Column(name = "contact_id")
    private String contactId;

    private  String eid;

    private String buid;

    @Column(name = "is_active")
    private boolean active;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private Long created_by;

    private LocalDateTime updated_at;

    private Long updated_by;

    private Long leadStatus;

    @Column(name = "next_follow_up")
    private LocalDateTime nextFollowUp;

    private String note;

}
