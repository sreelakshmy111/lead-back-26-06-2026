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

    private  String parentId;

    private String eid;

    private String buid;

    private boolean active;

    private LocalDateTime created_at;

    private Long created_by;

    private LocalDateTime updated_at;

    private Long updated_by;

}
