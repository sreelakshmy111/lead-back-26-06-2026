package com.project.Permission.of.lead.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "door_number",length = 255)
    private String doorNumber;

    @Column(name = "building_name",length = 255)
    private String buildingName;

    @Column(name = "street",length = 255)
    private String street;

    @Column(name = "locality",length = 255)
    private String locality;

    @Column(name = "area",length = 255)
    private String area;

    @Column(name = "city",length = 255)
    private String city;

    @Column(name = "state",length = 255)
    private String state;

    @Column(name = "country",length = 255)
    private String country;

    @Column(name = "zip_code",length = 255)
    private String zipCode;


}
