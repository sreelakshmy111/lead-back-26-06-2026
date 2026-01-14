package com.project.Permission.of.lead.service;

import com.project.Permission.of.lead.dto.CustomerDto;
import com.project.Permission.of.lead.entity.Users;

import java.util.List;

public interface CustomerService {
    CustomerDto createCustomer(CustomerDto customerDto, String eid, String buid, Users loggedInUser);

    List<CustomerDto> getCustomers(Users loggedInUser, String eid, String buid);

    CustomerDto updateContact(CustomerDto customerDto, Users loggedUser, String eid, String buid, String cuid);
}
