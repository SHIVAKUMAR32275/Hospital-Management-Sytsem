package com.example.HospitalManagementSystem.Services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String email, String token) {
        String subject = "Email Verification - Hospital Management System";
        String link = "http://localhost:8080/api/auth/verify?token=" + token;
        String body = "Click the link to verify your email:\n" + link;

        sendEmail(email, subject, body);
    }

    public void sendPasswordResetEmail(String email, String token) {
        String subject = "Password Reset - Hospital Management System";
        String link = "http://localhost:8080/api/auth/reset-password?token=" + token;
        String body = "Click the link to reset your password:\n" + link;

        sendEmail(email, subject, body);
    }

    private void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);

            mailSender.send(message);
            System.out.println("✅ Email sent to: " + to);
        } catch (MessagingException e) {
            System.err.println("❌ Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
