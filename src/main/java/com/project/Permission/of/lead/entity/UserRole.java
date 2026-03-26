package com.project.Permission.of.lead.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;


@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "user_role")
public class UserRole implements Serializable {


    @EmbeddedId
    private UserRoleId id;

    @ManyToOne(fetch = FetchType.LAZY)
//    @MapsId("uid") // This tells JPA to map to the key in UserRoleId
    @JoinColumn(
            name = "uid",
            referencedColumnName = "uid",
            insertable = false,
            updatable = false
    )
//    @JoinColumn(name = "uid")
    private Users user;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Roles role;

    // Optional: add extra fields (e.g., createdAt, assignedBy, etc.)

    public UserRole() {}

    public UserRole(Users user, Roles role) {
        this.user = user;
        this.role = role;
        this.id = new UserRoleId(user.getUid(), role.getRoleId());
    }

    // Getters and Setters
    public UserRoleId getId() {
        return id;
    }

    public void setId(UserRoleId id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}
