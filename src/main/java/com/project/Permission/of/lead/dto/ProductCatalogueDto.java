package com.project.Permission.of.lead.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductCatalogueDto {

    private Long id;
    private String product;
    private  String classification;
    private String description;
    private String pid;
    private String parentId;
    private String eid;
    private String buid;
    private boolean active;
    private LocalDateTime created_at;
    private Long created_by;
    private LocalDateTime updated_at;
    private Long updated_by;


}
