package com.project.Permission.of.lead.controller;


import com.project.Permission.of.lead.dto.UserRoleDto;
import com.project.Permission.of.lead.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user_role")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @PostMapping("/assign")
    public UserRoleDto assignRoleToUser(@RequestBody UserRoleDto dto) {

        return userRoleService.assignRoleToUser(dto);
    }

}
