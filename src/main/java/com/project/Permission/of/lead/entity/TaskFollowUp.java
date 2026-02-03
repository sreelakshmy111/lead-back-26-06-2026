package com.project.Permission.of.lead.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "task_followup")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskFollowUp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(name = "bussiness_id")
    private String bussinessUnitId;

    @Column(name = "lead_id")
    private String leadId;

    @Column(name = "next_followup_date")
    private LocalDateTime nextFollowUp;

    private String note;

    private LocalDateTime createdDate;

    private Long createdBy;

    private LocalDateTime updatedDate;

    private Long updatedBy;
}
