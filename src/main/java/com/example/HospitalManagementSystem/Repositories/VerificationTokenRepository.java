package com.example.HospitalManagementSystem.Repositories;


import com.example.HospitalManagementSystem.Models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
}

