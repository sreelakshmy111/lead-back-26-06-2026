package com.project.Permission.of.lead.service.PersonalManagementImpl;

import com.project.Permission.of.lead.dto.EmployeeRoleDto;
import com.project.Permission.of.lead.dto.PersonalManagementDto;
import com.project.Permission.of.lead.entity.*;
import com.project.Permission.of.lead.mapper.PearsonalMapper;
import com.project.Permission.of.lead.repository.*;
import com.project.Permission.of.lead.service.EmailServiceImpl.EmailServiceImpl;
import com.project.Permission.of.lead.service.PersonalManagementService;
import com.project.Permission.of.lead.util.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRolerepository userRolerepository;

    @Override
    public PersonalManagementDto createEmployee(PersonalManagementDto personalManagementDto, Users loggedInUser, String eid) {

        String email=personalManagementDto.getEmail();

        boolean exist=personalRepository.existsByEmail(email);
        if(exist){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Email already exists");
        }



        String emp_id=jdbcTemplate.queryForObject(
                "SELECT create_entity_id(?)",
                 new Object[]{"EMPLOYEE"},
                 String.class
                );

        Enterprise enterprise=enterpriseRepostory.findByEid(eid).
                orElseThrow(()-> new RuntimeException("Enterprise not found"));


        PersonalManagement personalManagement = PearsonalMapper.maptoPersonalManagement(personalManagementDto,loggedInUser.getUid(),eid);
        personalManagement.setActive(true);
        personalManagement.setEmpId(emp_id);
        PersonalManagement savedEmployee=personalRepository.save(personalManagement);

//        String pw=PasswordGenerator.generatePassword();
//        emailService.sendPasswordEmail(savedEmployee.getEmail(),pw);


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

        person.setFirstName(personalDto.getFirstName());
        person.setMiddleName(personalDto.getMiddleName());
        person.setLastName(personalDto.getLastName());
        person.setGender(personalDto.getGender());
        person.setDob(personalDto.getDob());
        person.setIsdCode(personalDto.getIsdCode());
        person.setPhone(personalDto.getPhone());
        person.setEmail(personalDto.getEmail());
        person.setQualification(personalDto.getQualification());
        person.setExperience(personalDto.getExperience());
        person.setActive(personalDto.isActive());
        person.setUpdated_at(LocalDateTime.now());
        person.setUpdated_by(loggedUser.getUid());

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


///  assign territories and get ..............................................
///
    @Override
    public List<PersonalManagementDto> assignTerritory(List<PersonalManagementDto> personalManagementDto, Users loggedUser, String eid, String empid, List<String> territoriesIds) {
        Enterprise enterprise=enterpriseRepostory.findByEid(eid).
                orElseThrow(()->new RuntimeException("Enterprise not found"));

        PersonalManagement personalManagement =personalRepository.findByEmpId(empid).
                orElseThrow(()->new RuntimeException("Employee not found"));

//        List<Teritory> territoryIds=territoryRepoitory.findByTidIn(territoriesIds);
//
//        if (territoryIds.isEmpty()) {
//            throw new RuntimeException("No valid territories found for the given IDs");
//        }
//
//        personalManagement.setTerritoryId(territoriesIds);
//        personalRepository.save(personalManagement);
//        return List.of(PearsonalMapper.maptoPersonalManagementDto(personalManagement));

        // 1️⃣ Existing territories
        List<String> existingTerritories = personalManagement.getTerritoryId();

        if (existingTerritories == null) {
            existingTerritories = List.of();
        }

        // 2️⃣ Check already assigned territories
        List<String> alreadyAssigned = territoriesIds.stream()
                .filter(existingTerritories::contains)
                .toList();

        if (!alreadyAssigned.isEmpty()) {
            throw new RuntimeException(
                    "Territory already assigned: " + alreadyAssigned
            );
        }

        // 3️⃣ Validate territory IDs
        List<Teritory> territoryEntities =
                territoryRepoitory.findByTidIn(territoriesIds);

        if (territoryEntities.isEmpty()) {
            throw new RuntimeException("No valid territories found for the given IDs");
        }

        // 4️⃣ Merge old + new territories
        List<String> updatedTerritories =
                Stream.concat(existingTerritories.stream(), territoriesIds.stream())
                        .distinct()
                        .toList();

        personalManagement.setTerritoryId(updatedTerritories);
        personalRepository.save(personalManagement);

        return List.of(
                PearsonalMapper.maptoPersonalManagementDto(personalManagement)
        );

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


    /// Get territories under the employeeeee.....................................................

    @Override
    public List<PersonalManagementDto> getTerritoriesUnderEmployee(String eid, String empid, Users loggedInUser) {
        Enterprise enterprise=enterpriseRepostory.findByEid(eid).
                orElseThrow(()-> new RuntimeException("enterprise not found"));

        // 2️⃣ Get employee personal management
        PersonalManagement personalManagement = personalRepository.findByEmpId(empid)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return List.of(
                PearsonalMapper.maptoPersonalManagementDto(personalManagement)
        );


    }

    @Override
    public boolean getEmployeeByEmail(String email) {

        boolean emp=personalRepository.existsByEmail(email);
       return emp;
     }

     ///  SERACH EMPLOYEESSS...................................................


    @Override
    public List<PersonalManagementDto> getSearchEmployee(String keyword, String eid, String buid) {


        List<PersonalManagement> employee=personalRepository.searchEmployeeByEnterpriseAndBussinessUnit(keyword,eid,buid);

        return employee.stream().map(a->PearsonalMapper.maptoPersonalManagementDto(a)).collect(Collectors.toList());



    }




    /// GET EMPLOYEEE AFTER REGISTER,CHECK DOES IT EXIST
//    @Override
//    public boolean getEmployeeByLoggedIn(PersonalManagementDto dto) {
//        String email=
//       boolean emp=personalRepository.existsByEmail(loggedInUser.getEmail());
//       return emp;
//    }




    // cloudflare function.............................................

    private void regitserToCloud(String email,String tower){

        RestTemplate restTemplate=new RestTemplate();

        HttpHeaders headers=new HttpHeaders();
        headers.setBasicAuth("admin","password");
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String,String> body=new HashMap<>();
        body.put("userID",email);
        body.put("tower",tower);

        HttpEntity<Map<String, String>> request =
                new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    "https://new.sudmun.workers.dev/user",
                    request,
                    String.class
            );

            System.out.println("Worker response: " + response.getStatusCode());

        } catch (Exception e) {
            System.out.println("Error calling Worker: " + e.getMessage());
        }

    }





    ///  ASSIGN EMPLOYEE A ROLE.................................................

    @Override
    public EmployeeRoleDto assignEmployeeRole(String employeeId, List<Long> roleIds, String eid, String buid, String tower) {

        PersonalManagement p=personalRepository.findByEmpId(employeeId).
                orElseThrow(()-> new RuntimeException("employee not found"));

        Users user = userRepository.findByEmail(p.getEmail());

        if(user == null){

            String pw = PasswordGenerator.generatePassword();

            System.out.println("password generated"+pw);


            String usr_id=jdbcTemplate.queryForObject(
                    "SELECT create_entity_id(?)",
                    new Object[]{"USER"},
                    String.class
            );

            user = new Users();
            user.setEmail(p.getEmail());
            user.setPassword(passwordEncoder.encode(pw));
            user.setUid(usr_id);

            user = userRepository.save(user);

            System.out.println("Sending email to: " + user.getEmail());

            emailService.sendPasswordEmail(user.getEmail(), pw);
        }


        for(Long roleId : roleIds){

            Roles role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Role not found"));

            UserRole ur = new UserRole(user, role);
            userRolerepository.save(ur);
        }



        regitserToCloud(user.getEmail(),tower);


        EmployeeRoleDto dto = new EmployeeRoleDto();
        dto.setEmployeeId(employeeId);
        dto.setRoleId(roleIds);

        return dto;


    }

}
