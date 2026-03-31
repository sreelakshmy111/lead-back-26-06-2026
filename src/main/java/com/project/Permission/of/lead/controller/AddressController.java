package com.project.Permission.of.lead.controller;

import com.project.Permission.of.lead.dto.AddressDto;
import com.project.Permission.of.lead.service.AddressService;
import com.project.Permission.of.lead.service.AddressServiceImpl.AddressServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class AddressController {

    @Autowired
    private AddressService addressService;


    @PutMapping("addresses/{aid}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable("aid") Long addressId,
                                                    @RequestBody AddressDto addressDto) {
        AddressDto addressDto1=addressService.updateAddress(addressId,addressDto);
        return ResponseEntity.ok(addressDto1);
    }


    @PostMapping("addresses")
    public ResponseEntity<AddressDto> createAddress(@RequestBody  AddressDto addressDto) {
        AddressDto addressDtos=addressService.createAddress(addressDto);
        return ResponseEntity.ok(addressDtos);
    }



    @GetMapping("addresses")
    public ResponseEntity<List<AddressDto>> getAllAddresses() {
        List<AddressDto> addressDtos=addressService.getAllAddress();
        return ResponseEntity.ok(addressDtos);
    }




    @PutMapping("/addresses/link")
    public ResponseEntity<?>linkAddress(@RequestBody Map<String,Object> requestData) {
        String message=addressService.linkAddress(requestData);
        return ResponseEntity.ok("Address linked");
    }



}
