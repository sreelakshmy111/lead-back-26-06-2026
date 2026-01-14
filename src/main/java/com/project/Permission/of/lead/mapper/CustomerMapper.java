package com.project.Permission.of.lead.mapper;

import com.project.Permission.of.lead.dto.CustomerDto;
import com.project.Permission.of.lead.entity.Customer;

import java.time.LocalDateTime;

public class CustomerMapper {

    public static Customer maptoCustomer(CustomerDto customerDto, String enterpriseId, String bussinessUnitId, Long userId) {
        LocalDateTime now=LocalDateTime.now();
        return new Customer(
                customerDto.getId(),
                customerDto.getCustId(),
                customerDto.getFirst_name(),
                customerDto.getLast_name(),
                customerDto.getGender(),
                customerDto.getDate_of_birth(),
                customerDto.getAddress_id(),
                customerDto.getIsd_code(),
                customerDto.getPhone(),
                customerDto.getEmail(),
                enterpriseId,
                bussinessUnitId,
                customerDto.is_active(),
                customerDto.getCreated_at()!=null? customerDto.getCreated_at():now,
                null,
                customerDto.getUpdated_at(),
                null

        );
    }


    public static CustomerDto maptoCustomerDto(Customer customer) {
        LocalDateTime now=LocalDateTime.now();
        return new CustomerDto(
                customer.getId(),
                customer.getCustId(),
                customer.getFirst_name(),
                customer.getLast_name(),
                customer.getGender(),
                customer.getDate_of_birth(),
                customer.getAddress_id(),
                customer.getIsd_code(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getEid(),
                customer.getBuid(),
                customer.is_active(),
                customer.getCreated_at(),
                customer.getCreated_by(),
                customer.getUpdated_at(),
                customer.getUpdated_by()

        );
    }


}
