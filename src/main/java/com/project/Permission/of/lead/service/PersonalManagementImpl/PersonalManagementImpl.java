package com.project.Permission.of.lead.service.PersonalManagementImpl;

import com.project.Permission.of.lead.dto.PersonalManagementDto;
import com.project.Permission.of.lead.entity.*;
import com.project.Permission.of.lead.mapper.PearsonalMapper;
import com.project.Permission.of.lead.repository.*;
import com.project.Permission.of.lead.service.PersonalManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonalManagementImpl implements PersonalManagementService {

    @Autowired
    private PersonalRepository personalRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EnterpriseRepostory enterpriseRepostory;

    @Autowired
    private BussinessUnitRepository bussinessUnitRepository;

    @Autowired
    private TeritoryRepoitory territoryRepoitory;

    @Autowired
    private UserRepository userRepository;

    @Override
    public PersonalManagementDto createEmployee(PersonalManagementDto personalManagementDto, Users loggedInUser, String eid) {

        String emp_id=jdbcTemplate.queryForObject(
                "SELECT create_entity_id(?)",
                 new Object[]{"EMPLOYEE"},
                 String.class
                );

        Enterprise enterprise=enterpriseRepostory.findByEid(eid).
                orElseThrow(()-> new RuntimeException("Enterprise not found"));


        PersonalManagement personalManagement = PearsonalMapper.maptoPersonalManagement(personalManagementDto,loggedInUser.getUser_id(),eid);
        personalManagement.setActive(true);
        personalManagement.setEmpId(emp_id);
        PersonalManagement savedEmployee=personalRepository.save(personalManagement);
        return PearsonalMapper.maptoPersonalManagementDto(savedEmployee);
    }

    ///  employeee under eid.......................................................

    @Override
    public List<PersonalManagementDto> getAllEmployee(String eid) {

        Enterprise enterprise=enterpriseRepostory.findByEid(eid).
                orElseThrow(()-> new RuntimeException("Enterprise not found"));


        List<PersonalManagement> employees=personalRepository.findByEid(eid);
        return employees.stream().map(p->PearsonalMapper.maptoPersonalManagementDto(p)).collect(Collectors.toList());

    }

    @Override
    public PersonalManagementDto updateEmployeeById(PersonalManagementDto personalDto, Users loggedUser, String eid, String empid) {

        Enterprise enterprise=enterpriseRepostory.findByEid(eid).
                orElseThrow(()-> new RuntimeException("Enterprise not found"));




        PersonalManagement person=personalRepository.findByEmpId(empid).
                orElseThrow(()->new RuntimeException("Employee not found"));

        person.setName(personalDto.getName());
        person.setGender(personalDto.getGender());
        person.setDob(personalDto.getDob());
        person.setIsdCode(personalDto.getIsdCode());
        person.setPhone(personalDto.getPhone());
        person.setEmail(personalDto.getEmail());
        person.setQualification(personalDto.getQualification());
        person.setExperience(personalDto.getExperience());
        person.setActive(personalDto.isActive());
        person.setUpdated_at(LocalDateTime.now());
        person.setUpdated_by(loggedUser.getUser_id());

        personalRepository.save(person);
        return PearsonalMapper.maptoPersonalManagementDto(person);
    }



    @Override
    public PersonalManagementDto asssignBussinessUnit(PersonalManagementDto personalManagementDto, String eid, String empid,String buid) {
        Enterprise enterprise=enterpriseRepostory.findByEid(eid).
                orElseThrow(()->new RuntimeException("Enterprise not found"));

        PersonalManagement personalManagement =personalRepository.findByEmpId(empid).
                orElseThrow(()->new RuntimeException("Employee not found"));

        BussinessUnit bussinessUnit=bussinessUnitRepository.findByBuid(buid).
                orElseThrow(()->new RuntimeException("BussinessUnit not found"));



     //   // 3️⃣ Create or update user...............................
//
//        Users user = userRepository.findByEmpId(employeeId)
//                .orElseGet(() -> {
//                    Users u = new Users();
////                    u.setEmpId(empId);
//                       u.setUsername(personalManagement.getName());
//                       u.setEmail(personalManagement.getEmail());
//                       u.setAddressId(personalManagement.getAddressId());
//                      return u;
//                });


        personalManagement.setBuid(bussinessUnit.getBuid());
        personalRepository.save(personalManagement);
//        userRepository.save(user);
        return PearsonalMapper.maptoPersonalManagementDto(personalManagement);

    }

    @Override
    public List<PersonalManagementDto> assignTerritory(List<PersonalManagementDto> personalManagementDto, Users loggedUser, String eid, String empid, List<String> territoriesIds) {
        Enterprise enterprise=enterpriseRepostory.findByEid(eid).
                orElseThrow(()->new RuntimeException("Enterprise not found"));

        PersonalManagement personalManagement =personalRepository.findByEmpId(empid).
                orElseThrow(()->new RuntimeException("Employee not found"));

        List<Teritory> territoryIds=territoryRepoitory.findByTidIn(territoriesIds);

        if (territoryIds.isEmpty()) {
            throw new RuntimeException("No valid territories found for the given IDs");
        }

        personalManagement.setTerritoryId(territoriesIds);
        personalRepository.save(personalManagement);
        return List.of(PearsonalMapper.maptoPersonalManagementDto(personalManagement));
    }


// get employees under eid and buid..........................................

    @Override
    public List<PersonalManagementDto> getEmployeeByEidAndBuid(Users loggedInUser, String eid, String buid) {
        Enterprise enterprise=enterpriseRepostory.findByEid(eid).
                orElseThrow(()->new RuntimeException("Enterprise not found"));


        BussinessUnit bussinessUnit=bussinessUnitRepository.findByBuid(buid).
                orElseThrow(()->new RuntimeException("BussinessUnit not found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("bussiness unit not belong to this enterprise");
        }

        List<PersonalManagement> personalManagement =personalRepository.findByEidAndBuid(eid,buid);

        return personalManagement.stream().map(p->PearsonalMapper.maptoPersonalManagementDto(p)).collect(Collectors.toList());



    }


    /// .............get employeeee by  territory and eid and buid......................
    @Override
    public List<PersonalManagementDto> getEmployeeByEidAndBuidAndTid(Users loggedInUser, String eid, String buid, String tid) {
        Enterprise enterprise=enterpriseRepostory.findByEid(eid).
                orElseThrow(()->new RuntimeException("Enterprise not found"));


        BussinessUnit bussinessUnit=bussinessUnitRepository.findByBuid(buid).
                orElseThrow(()->new RuntimeException("BussinessUnit not found(in territory get)"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("bussiness unit not belong to this enterprise");
        }

//        Teritory teritory=territoryRepoitory.findByTid(territoryId).
//                orElseThrow(()-> new RuntimeException("Territory not found"));

        List<PersonalManagement> employee =
                personalRepository
                        .findByEidAndBuidAndTerritory(
                                eid,
                                buid,
                                tid
                        );

       return employee.stream().map(p->PearsonalMapper.maptoPersonalManagementDto(p)).collect(Collectors.toList());
    }


    //........get employees under territories..................................

    @Override
    public List<PersonalManagementDto> getEmployeesUnderTerritory(String eid, String buid, String tid, Users loggedInUser) {
        Enterprise enterprise=enterpriseRepostory.findByEid(eid).
                orElseThrow(()-> new RuntimeException("enterprise not found"));

        BussinessUnit bussinessUnit=bussinessUnitRepository.findByBuid(buid).
                orElseThrow(()-> new RuntimeException("bussiness unit not found"));

        if(!bussinessUnit.getEnterpriseId().equals(eid)){
            throw new RuntimeException("busssinessunit not belong to enterprise");
        }

        Teritory teritory=territoryRepoitory.findByTid(tid).
                orElseThrow(()-> new RuntimeException("territory not found"));

//
//        if(!teritory.getBussinessUnitId().equals(buid)){
//            throw new RuntimeException("territory not belong to this bussiness unit");
//        }


        List<PersonalManagement> employes=personalRepository.findByAssignedTerritory(tid,buid);

        return employes.stream().map(a->PearsonalMapper.maptoPersonalManagementDto(a)).collect(Collectors.toList());
    }






}
