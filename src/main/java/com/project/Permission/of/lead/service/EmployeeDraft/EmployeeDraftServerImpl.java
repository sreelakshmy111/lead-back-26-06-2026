package com.project.Permission.of.lead.service.EmployeeDraft;

import com.project.Permission.of.lead.dto.EmployeeDraftDto;
import com.project.Permission.of.lead.dto.RegisterResponseDto;
import com.project.Permission.of.lead.entity.*;
import com.project.Permission.of.lead.mapper.EmployeeDraftMapper;
import com.project.Permission.of.lead.repository.*;
import com.project.Permission.of.lead.service.EmployeeDraftService;
import com.project.Permission.of.lead.service.UserServiceImpl.JWTService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class EmployeeDraftServerImpl implements EmployeeDraftService {

@Autowired
private EmployeeDraftRepository employeeDraftRepository;

@Autowired
private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepo;


    @Autowired
    private UserRolerepository userRolerepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private PersonalRepository personalRepository;




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





    /// CREATE EMPLOYEEE AFTER REGISTRATION.......................................

    @Override
    public RegisterResponseDto createEmployeeAfterRegister(EmployeeDraftDto employeeDraftDto) {

        String email=employeeDraftDto.getEmail();
        String password=employeeDraftDto.getPassword();


        //  Duplicate check in employee draft................

        if (employeeDraftRepository.existsByEmail(email)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already exists in employee draft"
            );
        }

        //  Duplicate check in users table.............................

        if (userRepository.existsByEmail(email)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already exists in users table"
            );
        }


        //  Create User..................................................

        Users user = new Users();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        Users createdUser = userRepository.save(user);


        //  Assign Role..............................................

        Roles role = roleRepo
                .findByRoleName("ENTERPRISE_ADMIN")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        UserRole userRole = new UserRole(createdUser, role);
        userRolerepository.save(userRole);


        //  Create Employee Draft....................................

        EmployeeDraft employeeDraft =
                EmployeeDraftMapper.maptoEmployeeDraft(
                        employeeDraftDto,
                        createdUser.getUser_id()
                );

        employeeDraftRepository.save(employeeDraft);

        // 6️⃣ Generate Token (single role)
        String token = jwtService.generateToken(
                createdUser.getEmail(),
                role.getRoleName()
        );


        regitserToCloud(createdUser.getEmail(), employeeDraftDto.getTower());

        // 7️⃣ Prepare Response

        RegisterResponseDto response=new RegisterResponseDto();
        response.setToken(token);
        response.setEmpdId(employeeDraft.getId());
        response.setTower(employeeDraftDto.getTower());


        return response;



    }


    /// CHECK EMPLOYEEE EXIST BEFORE FIRST CREATION.........................
    @Override
    public boolean checkEmployeeByEmail(String email) {

        boolean emp=employeeDraftRepository.existsByEmail(email);
        return emp;
    }


    ///  move the meployee_draft table data to employee table....................
    @Override
    public void moveEmployeeDraft(Long createdBy, String eid) {
        EmployeeDraft e=employeeDraftRepository.findByCreatedBy(createdBy);
        System.out.println("employe draft details"+e);

        PersonalManagement p=new PersonalManagement();
        p.setFirstName(e.getFirstName());
        p.setMiddleName(e.getMiddleName());
        p.setLastName(e.getLastName());
        p.setGender(e.getGender());
        p.setDob(e.getDob());
        p.setIsdCode(e.getIsdCode());
        p.setPhone(e.getPhone());
        p.setEmail(e.getEmail());
        p.setQualification(e.getQualification());
        p.setExperience(e.getExperience());
        p.setActive(e.isActive());
        p.setEid(eid);
        p.setAddressId(e.getAddressId());

         personalRepository.save(p);

    }




}
