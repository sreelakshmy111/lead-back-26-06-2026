package com.project.Permission.of.lead.service.EmailServiceImpl;

import com.project.Permission.of.lead.service.EmailService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

@Service
@Transactional
public class EmailServiceImpl implements EmailService {


    @Autowired
    private JavaMailSender mailSender;

    public void sendPasswordEmail(String toEmail, String password) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject("Your Account Password");

        message.setText(
                "Your employee account has been created.\n\n" +
                        "Email : " + toEmail + "\n" +
                        "Password : " + password + "\n\n" +
                        "Please change your password after login."
        );

        mailSender.send(message);
    }
}
