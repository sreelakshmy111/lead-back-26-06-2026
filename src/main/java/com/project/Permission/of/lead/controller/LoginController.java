package com.project.Permission.of.lead.controller;

import com.project.Permission.of.lead.dto.LoginResponseDto;
import com.project.Permission.of.lead.dto.UserDto;
import com.project.Permission.of.lead.entity.TokenData;
import com.project.Permission.of.lead.entity.Users;
import com.project.Permission.of.lead.repository.UserRepository;
import com.project.Permission.of.lead.service.BussinessUnitService;
import com.project.Permission.of.lead.service.EmailService;
import com.project.Permission.of.lead.service.UserDetails.UserPrinciple;
import com.project.Permission.of.lead.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController

public class LoginController {

@Autowired
    private UserService userService;

@Autowired
private UserRepository userRepository;

@Autowired
private EmailService emailService1;

@Autowired
private BussinessUnitService bussinessUnitService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto,HttpSession session) {

        String sessionCaptcha = (String) session.getAttribute("captcha");
        System.out.println("SESSION CAPTCHA: " + sessionCaptcha);
        System.out.println("USER CAPTCHA: " + userDto.getCaptcha());

//        if(sessionCaptcha == null ||
//                !sessionCaptcha.equalsIgnoreCase(userDto.getCaptcha())) {
//
//            return ResponseEntity.badRequest().body("Invalid Captcha");
//        }
//        session.removeAttribute("captcha");

        LoginResponseDto response = userService.verify(userDto);

        return ResponseEntity.ok(response);
    }



    // 🔹 CAPTCHA API
//    @GetMapping("/captcha")
//    public void getCaptcha(HttpSession session, HttpServletResponse response) throws Exception {
//
//        String captchaText = UUID.randomUUID().toString().substring(0,5);
//
//
//        session.setAttribute("captcha", captchaText);
//
//        int width = 150;
//        int height = 50;
//
//        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        Graphics g = image.getGraphics();
//
//        g.setColor(Color.WHITE);
//        g.fillRect(0,0,width,height);
//
//        g.setColor(Color.BLACK);
//        g.setFont(new Font("Arial", Font.BOLD, 30));
//        g.drawString(captchaText, 20, 35);
//
//        response.setContentType("image/png");
//
//        ImageIO.write(image,"png",response.getOutputStream());
//    }


//
//    @PostMapping("/login")
//    public LoginResponseDto login(@RequestBody UserDto userDto) {
//        return userService.verify(userDto);
//    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello Authenticated User!";
    }


/// / select bussiness unit after enterprise admin logged in................................

@PostMapping("/set-privilege")
public ResponseEntity<?> create(
        @AuthenticationPrincipal UserPrinciple userPrinciple, @PathVariable String buid) {

    bussinessUnitService.validBuAccess(userPrinciple, buid);

    return ResponseEntity.ok("OK");
}


}
