package com.example.HospitalManagementSystem.Repositories;

import com.example.HospitalManagementSystem.Models.RefreshToken;
import com.example.HospitalManagementSystem.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
}
