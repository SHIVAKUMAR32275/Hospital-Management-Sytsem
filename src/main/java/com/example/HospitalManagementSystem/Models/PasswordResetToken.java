package com.example.HospitalManagementSystem.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private LocalDateTime expiryDate;

    @OneToOne
    private User user;

    // Getters and Setters
}
