package com.example.HospitalManagementSystem.Services;

import com.example.HospitalManagementSystem.Kafka.KafkaProducerService;
import com.example.HospitalManagementSystem.Models.User;
import com.example.HospitalManagementSystem.Models.RefreshToken;
import com.example.HospitalManagementSystem.Repositories.UserRepository;
import com.example.HospitalManagementSystem.Repositories.RefreshTokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthenticationServices implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Block login if the user hasn't verified their email
        if (!user.isVerified()) {
            throw new UsernameNotFoundException("Email not verified");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }

    // ✅ Send verification email via Kafka
    public void sendVerificationEmail(String email, String token) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("email", email);
            map.put("token", token);
            String json = new ObjectMapper().writeValueAsString(map);
            kafkaProducerService.sendVerificationEmail(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    // ✅ Send password reset email via Kafka
    public void sendResetEmail(String email, String token) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("email", email);
            map.put("token", token);
            String json = new ObjectMapper().writeValueAsString(map);
            kafkaProducerService.sendPasswordResetEmail(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    // ✅ Create or Replace Refresh Token with proper transaction management
    @Transactional
    public RefreshToken createRefreshToken(User user) {
        refreshTokenRepository.deleteByUser(user); // ensure one refresh token per user
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(7));
        return refreshTokenRepository.save(refreshToken);
    }
}
