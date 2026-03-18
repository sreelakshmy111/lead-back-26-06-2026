package com.project.Permission.of.lead.service;


import com.project.Permission.of.lead.dto.ChangePasswordDto;
import com.project.Permission.of.lead.dto.LoginResponseDto;
import com.project.Permission.of.lead.dto.TowerDto;
import com.project.Permission.of.lead.dto.UserDto;
import org.springframework.stereotype.Service;

@Service

public interface UserService {
    UserDto createUser(UserDto userDto);
//    LoginResponseDto verify(UserDto loginRequest);
     LoginResponseDto verify(UserDto loginRequest);

    TowerDto assignTower(TowerDto towerDto);

    void changePassword(ChangePasswordDto dto, String username);

//    UserDto upsertUser(String userId, String towerId);

//    UserDto getTower(String userId);
}
