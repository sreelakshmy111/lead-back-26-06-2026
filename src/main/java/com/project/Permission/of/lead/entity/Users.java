package com.project.Permission.of.lead.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

//import javax.management.relation.Role;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class Users {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long user_id;

   @Column(nullable = false,length = 255)
    private String username;

   @Column(nullable = false,length = 255)
    private String password;

   @Column(unique = true,nullable = false,length = 255)
    private String email;


    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<UserRole> userRoles = new HashSet<>();



    @Column(name = "address_id")
    private Long addressId;


    public Users(Long user_id, String username, String password, String email,Long addressId) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.addressId=addressId;
    }





}
