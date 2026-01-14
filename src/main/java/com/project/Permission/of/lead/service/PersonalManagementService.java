package com.project.Permission.of.lead.service;

import com.project.Permission.of.lead.dto.PersonalManagementDto;
import com.project.Permission.of.lead.entity.Users;

import java.util.List;

public interface PersonalManagementService {
    PersonalManagementDto createEmployee(PersonalManagementDto personalManagementDto, Users loggedInUser, String eid);

    List<PersonalManagementDto> getAllEmployee(String eid);

//    PersonalManagementDto updateById(Long id);

//    PersonalManagementDto updateByEmployeeId(PersonalManagementDto personDto, Long id, Users loggedInUser);

    PersonalManagementDto updateEmployeeById(PersonalManagementDto personalManagementDto, Users loggedUser, String eid, String empid);

    PersonalManagementDto asssignBussinessUnit(PersonalManagementDto personalManagementDto, String eid, String empid, String buid);

    List<PersonalManagementDto> assignTerritory(List<PersonalManagementDto> personalManagementDto, Users loggedUser, String eid, String empid, List<String> territoriesIds);

    List<PersonalManagementDto> getEmployeeByEidAndBuid(Users loggedInUser, String eid, String buid);

    List<PersonalManagementDto> getEmployeeByEidAndBuidAndTid(Users loggedInUser, String ed, String buid, String tid);


    List<PersonalManagementDto> getEmployeesUnderTerritory(String eid, String buid, String tid, Users loggedInUser);
}
