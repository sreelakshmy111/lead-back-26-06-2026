package com.project.Permission.of.lead.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable


//@EqualsAndHashCode
public class UserRoleId implements Serializable {


    @Column(name = "uid")  // Must match DB column name
    private String uid;

    @Column(name = "role_id")
    private Long roleId;

//    public UserRoleId(String uid, Long roleId) {}

    public UserRoleId() {}
    public UserRoleId(String uid, Long roleId) {
        this.uid = uid;
        this.roleId = roleId;
    }

    // Required for JPA (to correctly identify composite key)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRoleId)) return false;
        UserRoleId that = (UserRoleId) o;
        return Objects.equals(uid, that.uid) &&
                Objects.equals(roleId, that.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, roleId);
    }

    // Getters and Setters (or use Lombok if you prefer)
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

}
