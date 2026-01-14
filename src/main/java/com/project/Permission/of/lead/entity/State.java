package com.project.Permission.of.lead.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "state")
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "state_name",length = 255)
    private  String name;

    @Column(length = 255)
    private String description;

    @Column(name = "is_active")
    private boolean active;

    private LocalDateTime createdAt;


    @Column(name = "created_by_id")
    private Long createdBy;

    private LocalDateTime updatedAt;

    @Column(name = "updated_by_id")
    private Long updatedBy;

    @Column(name = "zone_id")
    private String zoneId;

    @Column(name = "country_id")
    private String countryId;

    @Column(name = "region_id")
    private String regionId;

    @Column(name = "bussiness_unit_id")
    private String bussinessUnitId;

    @Column(name = "sid")
    private String sid;

}
