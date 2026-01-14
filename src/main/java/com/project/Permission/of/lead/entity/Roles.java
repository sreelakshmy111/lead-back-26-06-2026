package com.project.Permission.of.lead.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "role")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Roles {

    @Id
    @Column()

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;


    @Column(unique = true, nullable = false)
    private String role_name;


    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(name = "is_disabled", nullable = false)
    private boolean isDisabled = false;

    @Column(name = "is_system_role")
    private Boolean isSystemRole = false;

    @Column(name = "is_self_service")
    private Boolean isSelfService = false;

    @Column(name = "position")
    private Integer position = 0;

    @Column(name = "identifier", unique = true)
    private String identifier;

    @Column(name = "legal_form_enum")
    private Integer legalFormEnum;


//    @OneToMany(mappedBy = "role")
//    private Set<Users> users; // FIXED: should be Set or List, not single Users

//
//    @ManyToMany (fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "role_permission",
//            joinColumns = @JoinColumn(name = "role_id"),
//            inverseJoinColumns = @JoinColumn(name = "permission_id")
//    )
@OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
private Set<RolePermission> rolePermissions;






    public Roles(Long roleId, String roleName, String description, boolean disabled, Boolean isSystemRole, Boolean isSelfService, Integer position, String identifier, Integer legalFormEnum) {
        this.role_id = roleId;
        this.role_name = roleName;
        this.description = description;
        this.isDisabled = disabled;
        this.isSystemRole = isSystemRole;
        this.isSelfService = isSelfService;
        this.position = position;
        this.identifier = identifier;
        this.legalFormEnum = legalFormEnum;

    }
}
