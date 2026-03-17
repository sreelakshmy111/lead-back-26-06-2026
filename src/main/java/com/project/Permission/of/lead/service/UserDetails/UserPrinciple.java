package com.project.Permission.of.lead.service.UserDetails;

import com.project.Permission.of.lead.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Transactional
public class UserPrinciple implements UserDetails {
    private final Users user1;

    public UserPrinciple(Users user1) {
        this.user1 = user1;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {


        // Add roles as authorities (with ROLE_ prefix)
        List<GrantedAuthority> roles = user1.getUserRoles().stream()
                .map(userRole -> new SimpleGrantedAuthority("ROLE_" + userRole.getRole().getRoleName()))
                .collect(Collectors.toList());

        // Optionally, add permissions too
        List<GrantedAuthority> permissions = user1.getUserRoles().stream()
                .flatMap(userRole -> userRole.getRole().getRolePermissions().stream())
                .map(rolePermission -> new SimpleGrantedAuthority(rolePermission.getPermission().getCode().toUpperCase()))
                .collect(Collectors.toList());

        roles.addAll(permissions); // merge roles and permissions
        return roles;


    }
//        return user1.getUserRoles().stream()                   // fetch all user roles
//                .flatMap(userRole -> userRole.getRole()       // get the role
//                        .getRolePermissions().stream())       // get role_permissions
//                .map(rolePermission -> rolePermission.getPermission()) // get permission
//                .map(permission -> new SimpleGrantedAuthority(permission.getCode().toUpperCase()))
//                .collect(Collectors.toSet());
//    }

//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.singleton(new SimpleGrantedAuthority("ROLE" ));
//    }

    @Override
    public String getPassword() {
        return user1.getPassword();
    }

    public Users getUser() {
        return this.user1;
    }

    @Override
    public String getUsername() {
        return user1.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }

    public String getRole() {

        return user1.getUserRoles()
                .stream()
                .findFirst()
                .map((userRole -> userRole.getRole().getRoleName()))
                .orElse(null);
    }
}
