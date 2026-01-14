package com.project.Permission.of.lead.service.UserServiceImpl;


import com.project.Permission.of.lead.dto.UserDto;
import com.project.Permission.of.lead.entity.Roles;
import com.project.Permission.of.lead.entity.Users;
import com.project.Permission.of.lead.mapper.UserMapper;
import com.project.Permission.of.lead.repository.RoleRepository;
import com.project.Permission.of.lead.repository.UserRepository;
import com.project.Permission.of.lead.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {



    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);


    @Autowired
    private UserRepository userRepo;


    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private JWTService jwtService;

    public UserDto createUser(UserDto userDto) {

        if (userRepo.existsByUsername(userDto.getUsername())) {
            System.out.println("user already exists");
            throw new RuntimeException("Username already exists");
        }

        // fetch role by id from DTO


        // map userDto to Users entity
        Users user = new Users();
        user.setUsername(userDto.getUsername());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());  // assign existing role

        // save user
        Users createdUser = userRepo.save(user);

        return UserMapper.mapToUserDto(createdUser);
    }


//    login verification  setup......................................

private AuthenticationManager authManager;


public String verify(UserDto userDto) {
        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));

        if (authentication.isAuthenticated()) {
            String username=userDto.getUsername();
            return jwtService.generateToken(username);
        }
        return "fail";


}



}