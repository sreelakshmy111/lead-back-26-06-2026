package com.project.Permission.of.lead.service;


import com.project.Permission.of.lead.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service

public interface UserService {
    UserDto createUser(UserDto userDto);
    String verify(UserDto userDto);
}
