package com.project.Permission.of.lead.entity.Catalogue;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_catalogue")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCatalogue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String product;

    private String classification;

    private String description;

    private String pid;

    @Column(name = "parent_id")
    private  String parentId;

    private String eid;

    private String buid;

    private boolean active;

    private LocalDateTime created_at;

    private String created_by;

    private LocalDateTime updated_at;

    private String updated_by;

}
