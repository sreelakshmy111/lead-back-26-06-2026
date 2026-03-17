package com.project.Permission.of.lead.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserDto {

    private Long user_id;           // optional, for responses
    private String uid;
    private String username;
    private String password;

    private String email;
    private Long addressId;
    private String tower;

    private String captcha;   // ✅ add this



    public UserDto(Long user_id, String username, String role, String email, String usersEmail, Long towerId) {
        this.user_id = user_id;

        this.username = username;
        this.password = null;
        this.email = null;
        this.addressId = null;

    }
}
