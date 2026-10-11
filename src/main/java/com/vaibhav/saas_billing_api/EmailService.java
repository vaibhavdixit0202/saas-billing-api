package com.vaibhav.saas_billing_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendWelcomeEmail(String toEmail, String userName) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject("Welcome to SaaS Pro Plan! 🚀");
        message.setText("Hi " + userName + ",\n\nWelcome to our SaaS Billing Platform. Your account has been successfully created and your pro plan is active!\n\nBest Regards,\nVaibhav's SaaS Team");

        mailSender.send(message);
        System.out.println("Welcome mail successfully sent to: " + toEmail);
    }
}