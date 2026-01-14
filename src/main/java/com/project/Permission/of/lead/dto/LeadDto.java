package com.project.Permission.of.lead.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LeadDto  {
    private Long lead_id;
    private String leadName;
    private String email;
    private String phone;
}
