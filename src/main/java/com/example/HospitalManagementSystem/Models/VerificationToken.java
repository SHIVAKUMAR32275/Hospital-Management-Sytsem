package com.example.HospitalManagementSystem.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private LocalDateTime expiryDate;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
