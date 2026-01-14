package com.project.Permission.of.lead.service;

import com.project.Permission.of.lead.dto.AddressDto;

import java.util.List;
import java.util.Map;

public interface AddressService {
    AddressDto createAddress(AddressDto addressDto);

    List<AddressDto> getAllAddress();


    String linkAddress(Map<String, Object> requestData);

    AddressDto updateAddress(Long addressId, AddressDto addressDto);
}
