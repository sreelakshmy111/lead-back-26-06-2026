package com.project.Permission.of.lead.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "region")
public class Region {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "region",length = 255)
    private String name;

    @Column(name = "description",length = 255)
    private String description;

    @Column(name = "is_active")
    private boolean active;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @Column(name = "created_by_id",length = 255)
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @Column(name = "updated_by_id",length = 255)
    private String updatedBy;


    @Column(name = "bussiness_unit_id")
    private String bussinessUnitId;


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
//        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Column(name = "rid")
    private String rid;

}