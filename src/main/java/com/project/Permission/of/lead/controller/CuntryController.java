package com.project.Permission.of.lead.controller;

import com.project.Permission.of.lead.dto.CountryDto;
import com.project.Permission.of.lead.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CuntryController {

    @Autowired
    private CountryService countryService;

    @GetMapping("/countries")
    public ResponseEntity <List<CountryDto>> countryDto(CountryDto countryDto){
        List<CountryDto> countryDtos=countryService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(countryDtos);
    }


}
