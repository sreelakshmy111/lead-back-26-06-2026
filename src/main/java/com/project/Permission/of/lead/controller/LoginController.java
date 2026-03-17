package com.project.Permission.of.lead.controller;

import com.project.Permission.of.lead.dto.LoginResponseDto;
import com.project.Permission.of.lead.dto.UserDto;
import com.project.Permission.of.lead.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpSession;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.UUID;

@RestController

public class LoginController {

@Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto,HttpSession session) {

        String sessionCaptcha = (String) session.getAttribute("captcha");
        System.out.println("SESSION CAPTCHA: " + sessionCaptcha);
        System.out.println("USER CAPTCHA: " + userDto.getCaptcha());

        if(sessionCaptcha == null ||
                !sessionCaptcha.equalsIgnoreCase(userDto.getCaptcha())) {

            return ResponseEntity.badRequest().body("Invalid Captcha");
        }
        session.removeAttribute("captcha");

        LoginResponseDto response = userService.verify(userDto);

        return ResponseEntity.ok(response);
    }



    // 🔹 CAPTCHA API
    @GetMapping("/captcha")
    public void getCaptcha(HttpSession session, HttpServletResponse response) throws Exception {

        String captchaText = UUID.randomUUID().toString().substring(0,5);


        session.setAttribute("captcha", captchaText);

        int width = 150;
        int height = 50;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0,0,width,height);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString(captchaText, 20, 35);

        response.setContentType("image/png");

        ImageIO.write(image,"png",response.getOutputStream());
    }


//
//    @PostMapping("/login")
//    public LoginResponseDto login(@RequestBody UserDto userDto) {
//        return userService.verify(userDto);
//    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello Authenticated User!";
    }


    /// FORGOT PASSWORD.......................................................
//
//    @PostMapping("/forgot-password")
//    public


}
