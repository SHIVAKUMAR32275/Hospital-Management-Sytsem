package com.example.HospitalManagementSystem.Kafka;

import com.example.HospitalManagementSystem.Services.MailService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @Autowired
    private MailService mailService;

    @KafkaListener(topics = "email-verification-topic", groupId = "email-group")
    public void consumeVerification(String message) {
        System.out.println("🔥 Kafka Consumer Triggered (email-verification-topic): " + message); // ADD THIS

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(message);
            String email = json.get("email").asText();
            String token = json.get("token").asText();
            mailService.sendVerificationEmail(email, token);
        } catch (Exception e) {
            System.err.println("❌ Error parsing verification message: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @KafkaListener(topics = "password-reset-topic", groupId = "email-group")
    public void consumePasswordReset(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(message);
            String email = json.get("email").asText();
            String token = json.get("token").asText();
            mailService.sendPasswordResetEmail(email, token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
