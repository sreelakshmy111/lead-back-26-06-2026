package com.project.Permission.of.lead.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "permission")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Permission {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long permission_id;

    @Column(name = "action_name", length = 100)
    private String actionName;

    @Column(name = "code", nullable = false, length = 100)
    private String code;

    @Column(name = "entity_name", length = 100)
    private String entityName;

    @Column(name = "grouping", length = 45)
    private String grouping;

    @Column(name = "can_maker_checker", nullable = false)
    private boolean canMakerChecker;

    @Column(name = "description", length = 255)
    private String description;

//    @ManyToMany(mappedBy = "permissions")
//    private Set<Roles> role;
@OneToMany(mappedBy = "permission", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
private Set<RolePermission> rolePermissions;


    public Permission(Long permissionId, String actionName, String code, String entityName, String grouping, boolean canMakerChecker, String description) {
    }
}
