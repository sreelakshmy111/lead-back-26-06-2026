package com.project.Permission.of.lead.controller;


import com.project.Permission.of.lead.dto.UserDto;
import com.project.Permission.of.lead.mapper.UserMapper;
import com.project.Permission.of.lead.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;


    @PostMapping("/create")

    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){

        UserDto createUsers= service.createUser(userDto);
        return ResponseEntity.ok(createUsers);
    }
}
