package com.project.Permission.of.lead.service;

import com.project.Permission.of.lead.dto.CheckUpDto;
import com.project.Permission.of.lead.dto.EnterpriseDto;
import com.project.Permission.of.lead.entity.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EnterpriseService {

    EnterpriseDto createEnterprise(EnterpriseDto enterpriseDto, Users loggedInUser);


    List<EnterpriseDto> getAll(Users loggedInUser);

    EnterpriseDto getenterpriseByEid(String enterpriseId);



//    EnterpriseDto createEnterprise(EnterpriseDto enterpriseDto, Users loggedInUser);

    EnterpriseDto updateEnterprise(String eid, EnterpriseDto updateEnterpriseDto, Users loggedInUser);

//    boolean isEnterpriseCeated(Users loggedInUser);

    CheckUpDto checkStatus(Users loggedInUser);

    ResponseEntity<EnterpriseDto> checkEnterpriseExist(Users loggedInUser);


//    EnterpriseDto getenterpriseById(Long id);
}
