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
@Table(name = "zone")
public class Zone {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "zone",length = 200)
    private String name;

    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "is_active")
    private boolean active;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @Column(name = "created_by_id")
    private String createdBy;


    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @Column(name = "updated_by_id")
    private String updatedBy;


    @Column(name = "country_id")
    private String countryId;


    @Column(name = "region_id")
    private String regionId;


    @Column(name = "bussiness_unit_id")
    private String bussinessUnitId;

    @Column(name = "zid")
    private String zid;


}
