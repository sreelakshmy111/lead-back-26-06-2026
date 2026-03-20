package com.project.Permission.of.lead.entity.Catalogue;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "service_catalogue")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceCatalogue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String service;

    private String classification;

    private String description;

    private String sid;

    @Column(name = "parent_id")
    private  String parentId;

    private String eid;

    private String buid;

    private boolean active;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "created_by")
    private String created_by;


    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @Column(name = "updated_by")
    private String updated_by;

}
