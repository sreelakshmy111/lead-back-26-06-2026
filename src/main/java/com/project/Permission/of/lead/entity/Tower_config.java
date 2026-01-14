package com.project.Permission.of.lead.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tower_config")
@AllArgsConstructor
@NoArgsConstructor
public class Tower_config {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "entity")
    private String entity;

    @Column(name = "entity_prefix")
    private String entityPrefix;


}
