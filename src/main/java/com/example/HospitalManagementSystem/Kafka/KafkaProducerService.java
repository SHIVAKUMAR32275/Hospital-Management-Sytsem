package com.example.HospitalManagementSystem.Kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String VERIFICATION_TOPIC = "email-verification-topic";
    private static final String RESET_TOPIC = "password-reset-topic";

    public void sendVerificationEmail(String message) {
        kafkaTemplate.send(VERIFICATION_TOPIC, message);
    }

    public void sendPasswordResetEmail(String message) {
        kafkaTemplate.send(RESET_TOPIC, message);
    }
}
