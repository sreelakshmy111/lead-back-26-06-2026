package com.project.Permission.of.lead.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressDto {

    private Long id;
    private String doorNumber;
    private String buildingName;
    private String street;
    private String locality;
    private String area;
    private String city;
    private String state;
    private String country;
    private String zipCode;


}
