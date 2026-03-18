package com.project.Permission.of.lead.controller;

import com.project.Permission.of.lead.dto.ChangePasswordDto;
import com.project.Permission.of.lead.entity.TokenData;
import com.project.Permission.of.lead.entity.Users;
import com.project.Permission.of.lead.repository.UserRepository;
import com.project.Permission.of.lead.service.EmailService;
import com.project.Permission.of.lead.service.UserDetails.UserPrinciple;
import com.project.Permission.of.lead.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class ForgotPasswordController {


    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService1;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /// FORGOT PASSWORD.......................................................

    private Map<String, TokenData> tokenStore= new ConcurrentHashMap<>();

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String,String> request){

        String email=request.get("email");
        String frontendUrl=request.get("frontendUrl");

        Users users=userRepository.findByEmail(email);

        if(users==null){
            return ResponseEntity.badRequest().body("User not found");
        }

        String token= UUID.randomUUID().toString();

        tokenStore.put(token,new TokenData(email, LocalDateTime.now().plusMinutes(15),false));

        String link = frontendUrl + "/reset-password?token=" + token;

        // 🔥 send email
        emailService1.sendResetLink(email, link);

        return ResponseEntity.ok("Reset link sent to email");

    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String,String> request){

        String token = request.get("token");
        String newPassword = request.get("newPassword");


        TokenData data = tokenStore.get(token);

        if(data == null){
            return ResponseEntity.badRequest().body("Invalid token");
        }

        if(data.isUsed()){
            return ResponseEntity.badRequest().body("Token already used");
        }

        if(data.getExpiry().isBefore(LocalDateTime.now())){
            return ResponseEntity.badRequest().body("Token expired");
        }

        Users user = userRepository.findByEmail(data.getEmail());

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        data.setUsed(true);

        return ResponseEntity.ok("Password updated successfully");
    }


    /// CHANGE PASSWORD....................................................

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@AuthenticationPrincipal UserPrinciple userPrinciple,
                                            @RequestBody ChangePasswordDto dto){


        userService.changePassword(dto,userPrinciple.getUsername());

        return ResponseEntity.ok("password change successfully");

    }


}
