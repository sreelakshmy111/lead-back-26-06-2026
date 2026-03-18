package com.project.Permission.of.lead.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenData {

    private String email;

    private LocalDateTime expiry;

    private boolean used;
}
