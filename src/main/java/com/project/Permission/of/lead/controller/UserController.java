package com.project.Permission.of.lead.controller;


import com.project.Permission.of.lead.dto.AssignUserTowerDto;
import com.project.Permission.of.lead.dto.TowerDto;
import com.project.Permission.of.lead.dto.UserDto;
import com.project.Permission.of.lead.mapper.UserMapper;
import com.project.Permission.of.lead.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

public class UserController {

    @Autowired
    private UserService service;


    @PostMapping("/users/create")

    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){

        UserDto createUsers= service.createUser(userDto);
        return ResponseEntity.ok(createUsers);
    }


    /// UPSERT USER WITH TOWERR.....................................................................

//    @PostMapping("/user")
//    public ResponseEntity<?>upsertTower(
//                                        @RequestBody AssignUserTowerDto request){
//
//        UserDto u=service.upsertUser(request.getUserId(),request.getTowerName());
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(u);
//
//
//    }


    /// Get the tower the user belongs...............................................................

//
//    @GetMapping("/user")
//    public ResponseEntity<?>getTower(
//            @RequestParam String userId){
//
//        UserDto u=service.getTower(userId);
//
//        return ResponseEntity.ok(u);
//
//
//    }


}
