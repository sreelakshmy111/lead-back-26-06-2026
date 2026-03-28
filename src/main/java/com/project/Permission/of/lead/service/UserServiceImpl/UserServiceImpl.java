package com.project.Permission.of.lead.service.UserServiceImpl;


import com.project.Permission.of.lead.dto.ChangePasswordDto;
import com.project.Permission.of.lead.dto.LoginResponseDto;
import com.project.Permission.of.lead.dto.TowerDto;
import com.project.Permission.of.lead.dto.UserDto;
import com.project.Permission.of.lead.entity.PersonalManagement;
import com.project.Permission.of.lead.entity.Roles;
import com.project.Permission.of.lead.entity.UserRole;
import com.project.Permission.of.lead.entity.Users;
import com.project.Permission.of.lead.mapper.UserMapper;
import com.project.Permission.of.lead.repository.*;
import com.project.Permission.of.lead.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.management.relation.Role;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {



    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);




    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TowerRepository towerRepository;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PersonalRepository personalRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserRolerepository userRolerepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HttpServletRequest request;




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




    public UserDto createUser(UserDto userDto) {

        if (userRepo.existsByEmail(userDto.getEmail())) {
            System.out.println("user already exists");
            throw new RuntimeException("Username already exists");
        }

        // fetch role by id from DTO


        String usr_id=jdbcTemplate.queryForObject(
                "SELECT create_entity_id(?)",
                new Object[]{"USER"},
                String.class
        );


        // map userDto to Users entity
        Users user = new Users();
        user.setUsername(userDto.getUsername());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());  // assign existing role
        user.setUid(usr_id);

        // save user
        Users createdUser = userRepo.save(user);


        // ADD TO EMPLOYEEE TABLE.................................................

//        PersonalManagement employee= new PersonalManagement();
//        employee.setName(userDto.getUsername());
//        employee.setEmpId(String.valueOf(userDto.getUser_id()));
//        employee.setEmail(user.getEmail());

//        personalRepository.save(employee);



        // SET ROLE AND SAVE IT INTO USER_ROLE  TABLE.................................
        Roles role = roleRepo
                .findByRoleName("ENTERPRISE_ADMIN")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        UserRole userRole = new UserRole(createdUser.getUid(), role.getRoleId());



        ;
        userRolerepository.save(userRole);



//        regitserToCloud(createdUser.getEmail(), userDto.getTower());

        return UserMapper.mapToUserDto(createdUser);
    }



/// GET CLOUDFLARE DETAILS...........................................................

private Map<String, Object> getCloudFlare(String email) {

    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setBasicAuth("admin", "password");

    HttpEntity<String> entity = new HttpEntity<>(headers);

    try {

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://new.sudmun.workers.dev/user?userID=" + email,
                HttpMethod.GET,
                entity,
                Map.class
        );

        System.out.println("Cloudflare RAW RESPONSE: " + response.getBody());

        return response.getBody();

    } catch (Exception e) {
        System.out.println("Error in getting tower details: " + e.getMessage());
        return null;
    }
}



//    login verification  setup......................................
@Autowired
private AuthenticationManager authManager;


//public String verify(UserDto userDto) {
//        Authentication authentication =
//                authManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
//
//        if (authentication.isAuthenticated()) {
//            String username=userDto.getUsername();
//            return jwtService.generateToken(username);
//        }
//
//    // 3️⃣ Generate JWT WITH role
//        String token= jwtService.generateToken(
//                user.getUsername(),
//                roleName
//        );
//
//    return "fail";
//
//
//}


public LoginResponseDto verify(UserDto userDto) {

    Authentication authentication =
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDto.getEmail(),
                            userDto.getPassword()

                    )
            );

    if (!authentication.isAuthenticated()) {
        throw new RuntimeException("Invalid credentials");
    }

    // 1️⃣ Load user from DB
    Users user = userRepo.findByEmail(userDto.getEmail());
    if (user == null) {
        throw new RuntimeException("User not found");
    }
//                .orElseThrow(() -> new RuntimeException("User not found"));

    // 2️⃣ Extract ROLE NAME (IMPORTANT)
    // adjust getter based on your mapping..............................

//    List<String> roles;

//    List<String> roles = null;
//    if (user.getUserRoles() != null && !user.getUserRoles().isEmpty()) {
//        roles = user.getUserRoles().stream().map(userRole -> userRole.getRole().getRoleName()).toList();
//
//
//    }

    List<Long> roleIds = userRolerepository.findByUid(user.getUid())
            .stream()
            .map(UserRole::getRoleId)
            .toList();

    List<String> roles = roleRepo.findAllById(roleIds)
            .stream()
            .map(Roles::getRoleName)
            .toList();


//    String roleName = user.getUserRoles()
//            .iterator()
//            .next()                                    /// ealier code....................................
//            .getRole()
//            .getRoleName();   // e.g. "BU_ADMIN", "SALES"


    //  Generate JWT WITH role
    //  Get tower name


    //  Generate token...................................

    String token = jwtService.generateToken(
            user.getEmail(),
            roles
    );

    Map<String, Object> cloudResponse = getCloudFlare(user.getEmail());


    String storedCaptcha = (String) request.getSession().getAttribute("captcha");

    if (storedCaptcha == null ||
            !storedCaptcha.equalsIgnoreCase(userDto.getCaptcha())) {
        throw new RuntimeException("Invalid Captcha");
    }

    LoginResponseDto response = new LoginResponseDto();
    response.setToken(token);
    response.setUserDto(UserMapper.mapToUserDto(user));
    response.setTower(cloudResponse);   // change type to Map

    return response;


}




    @Override
    public TowerDto assignTower(TowerDto towerDto) {
        return null;
    }


    /// Change pass word...............................................................

    @Override
    public void changePassword(ChangePasswordDto dto, String username) {

        String email=username;

        Users user=userRepo.findByEmail(email);

        if(user==null){
            throw new RuntimeException("user not exist");
        }

        if(!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())){
            throw new RuntimeException("old password not found");
        }

        if(!dto.getNewPassword().equals(dto.getConfirmPassword())){
            throw new RuntimeException("passsword matches");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepo.save(user);





    }
//
//    @Override
//    public UserDto upsertUser(String userId, String towerId) {
//
//        // 1️⃣ User MUST exist
//        Users user = userRepo.findByEmail(userId)
//                .orElseThrow(() ->
//                        new RuntimeException("User does not exist. Create user first.")
//                );
//        System.out.println("Email received: [" + userId + "]");
//
//        Tower tower = towerRepository.findByName(towerId)
//                .orElseGet(() -> {
//                    Tower t = new Tower();
//                    t.setName(towerId);
//                    return towerRepository.save(t);
//                });
//
//
//
//         user.setTowerId(tower.getId());
//        userRepo.save(user);
//
//        // 4️⃣ Build and return UserDto
//        UserDto dto = new UserDto();
//        dto.setUsername(user.getUsername());
//        dto.setEmail(user.getEmail());
//        dto.setTowerId(tower.getId());
//        dto.setTowerId(tower.getId());
//
//        return dto;
//
//    }

    /// GET TOWER.................................................


//    @Override
//
//    public UserDto getTower(String userId) {
//        Users user=userRepo.findByEmail(userId).orElseThrow(()-> new RuntimeException("user not exist"));
//
//        return UserMapper.mapToUserDto(user);
//    }
}