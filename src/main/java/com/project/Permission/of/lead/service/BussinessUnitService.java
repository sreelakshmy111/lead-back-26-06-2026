package com.project.Permission.of.lead.service;

import com.project.Permission.of.lead.dto.BussinessUnitDto;
import com.project.Permission.of.lead.entity.Users;

import java.util.List;

public interface BussinessUnitService {

//    BussinessUnitDto createBussinessEnterprise(BussinessUnitDto bussinessUnitDto, Users loggedInUser);

  

//    List<BussinessUnitDto> getB();

    List<BussinessUnitDto> getBussinessEnterpriseById(String eid);

    BussinessUnitDto createBussinessUnit(String eid, BussinessUnitDto bussinessUnitDto, Users currentUser);

    BussinessUnitDto updateBussinessEnterprise(Long enterpriseId, Long businessunitId, BussinessUnitDto bussinessUnitDto, Users currentUser);

    BussinessUnitDto updateBussinessUnit(BussinessUnitDto bussinessUnitDto, Users loggedUser, String buid, String eid);


    void linkAddress(Long enterpriseId, Long bussiessunitId, Long addressId);

    BussinessUnitDto getBussinessByIdEnterpriseById(String eid, String buid);
}
