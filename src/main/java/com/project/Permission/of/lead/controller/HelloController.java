package com.project.Permission.of.lead.controller;

import com.project.Permission.of.lead.dto.UserDto;
import com.project.Permission.of.lead.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HelloController {

@Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody UserDto userDto) {

        return userService.verify(userDto);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello Authenticated User!";
    }

}
