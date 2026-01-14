package com.project.Permission.of.lead.service;

import com.project.Permission.of.lead.dto.EnterpriseDto;
import com.project.Permission.of.lead.entity.Users;

import java.util.List;

public interface EnterpriseService {

    EnterpriseDto createEnterprise(EnterpriseDto enterpriseDto, Users loggedInUser);


    List<EnterpriseDto> getAll();

    EnterpriseDto getenterpriseByEid(String enterpriseId);



//    EnterpriseDto createEnterprise(EnterpriseDto enterpriseDto, Users loggedInUser);

    EnterpriseDto updateEnterprise(String eid, EnterpriseDto updateEnterpriseDto, Users loggedInUser);


//    EnterpriseDto getenterpriseById(Long id);
}
