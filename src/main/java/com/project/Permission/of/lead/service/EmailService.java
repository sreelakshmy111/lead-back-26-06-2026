package com.project.Permission.of.lead.service;

public interface EmailService {

    void sendPasswordEmail(String toEmail, String password);
    void sendResetLink(String email, String link);
}
