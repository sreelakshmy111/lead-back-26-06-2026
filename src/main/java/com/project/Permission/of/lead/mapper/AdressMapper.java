package com.project.Permission.of.lead.mapper;

import com.project.Permission.of.lead.dto.AddressDto;
import com.project.Permission.of.lead.entity.Address;

public class AdressMapper {


    public static Address mapToAddress(AddressDto addressDto) {

        return new Address (
                addressDto.getId(),
                addressDto.getDoorNumber(),
                addressDto.getBuildingName(),
                addressDto.getStreet(),
                addressDto.getLocality(),
                addressDto.getArea(),
                addressDto.getCity(),
                addressDto.getState(),
                addressDto.getState(),
                addressDto.getZipCode()
        );
    }

    public static AddressDto mapToAddressDto(Address address) {
        return new AddressDto (
                address.getId(),
                address.getDoorNumber(),
                address.getBuildingName(),
                address.getStreet(),
                address.getLocality(),
                address.getArea(),
                address.getCity(),
                address.getState(),
                address.getCountry(),
                address.getZipCode()
        );
    }
}
