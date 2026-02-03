package com.project.Permission.of.lead.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name ="lead_status_custom")
public class LeadStatusCustom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="enterprise_id")
    private String enterpriseId;

    @Column(name = "bu_id")
    private String bussinessUnitId;

    @Column(name = "status_code")
    private String statusCode;

    @Column(name = "status_description")
    private String statusDescription;

    @Column(name = "status_sequence")
    private Long statusSequence;

    @Column(name = "created_date")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_date")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;
}

