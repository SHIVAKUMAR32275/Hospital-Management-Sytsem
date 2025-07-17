package com.example.HospitalManagementSystem.Controllers;

import com.example.HospitalManagementSystem.DTOs.AuthenticationRequestDTO;
import com.example.HospitalManagementSystem.DTOs.AuthenticationResponseDTO;
import com.example.HospitalManagementSystem.Jwt.JwtUtil;
import com.example.HospitalManagementSystem.Models.PasswordResetToken;
import com.example.HospitalManagementSystem.Models.RefreshToken;
import com.example.HospitalManagementSystem.Models.User;
import com.example.HospitalManagementSystem.Models.VerificationToken;
import com.example.HospitalManagementSystem.Repositories.PasswordResetTokenRepository;
import com.example.HospitalManagementSystem.Repositories.RefreshTokenRepository;
import com.example.HospitalManagementSystem.Repositories.UserRepository;
import com.example.HospitalManagementSystem.Repositories.VerificationTokenRepository;
import com.example.HospitalManagementSystem.Services.AuthenticationServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired private UserRepository userRepository;
    @Autowired private VerificationTokenRepository tokenRepository;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private AuthenticationServices authenticationServices;
    @Autowired private PasswordResetTokenRepository resetTokenRepository;
    @Autowired private RefreshTokenRepository refreshTokenRepository;

    @Value("${app.token.expiration.minutes:30}")
    private int tokenExpiryMinutes;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // ✅ Register endpoint
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthenticationRequestDTO request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        String role = request.getRole().toUpperCase();
        if (!role.equals("USER") && !role.equals("ADMIN")) {
            return ResponseEntity.badRequest().body("Invalid role. Use 'USER' or 'ADMIN'.");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(LocalDateTime.now().plusMinutes(tokenExpiryMinutes));
        tokenRepository.save(verificationToken);

        // Delegate Kafka JSON creation and sending to the service
        authenticationServices.sendVerificationEmail(user.getUsername(), token);

        logger.info("User registered: {}", user.getUsername());
        return ResponseEntity.ok("Registration successful! Please check your email to verify your account.");
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (Exception ex) {
            logger.warn("Invalid login attempt for user: {}", request.getUsername());
            return ResponseEntity.badRequest().body("Invalid username or password");
        }

        User user = userRepository.findByUsername(request.getUsername()).get();
        String accessToken = jwtUtil.GenerateToken(user.getUsername());
        RefreshToken refreshToken = authenticationServices.createRefreshToken(user);

        return ResponseEntity.ok(new AuthenticationResponseDTO(accessToken, refreshToken.getToken()));
    }


    // ✅ Email verification endpoint
    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        Optional<VerificationToken> optionalToken = tokenRepository.findByToken(token);
        if (optionalToken.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid verification token");
        }

        VerificationToken verificationToken = optionalToken.get();
        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            logger.warn("Expired token attempted for verification: {}", token);
            return ResponseEntity.badRequest().body("Token expired");
        }

        User user = verificationToken.getUser();
        user.setVerified(true);
        userRepository.save(user);
        tokenRepository.delete(verificationToken);

        logger.info("Email verified for user: {}", user.getUsername());
        return ResponseEntity.ok("Email verified successfully. You can now log in.");
    }

    // ✅ Forgot Password - Send Reset Link
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        Optional<User> userOpt = userRepository.findByUsername(email);

        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("No account with that email");
        }

        User user = userOpt.get();
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));
        resetTokenRepository.save(resetToken);

        // Delegate reset email sending to the service
        authenticationServices.sendResetEmail(user.getUsername(), token);

        return ResponseEntity.ok("Reset link sent to your email.");
    }

    // ✅ Reset Password - Set New Password
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestBody Map<String, String> request) {
        Optional<PasswordResetToken> tokenOpt = resetTokenRepository.findByToken(token);
        if (tokenOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid token");
        }

        PasswordResetToken resetToken = tokenOpt.get();
        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Token expired");
        }

        String newPassword = request.get("newPassword");
        if (newPassword == null || newPassword.length() < 6) {
            return ResponseEntity.badRequest().body("Password must be at least 6 characters");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        resetTokenRepository.delete(resetToken);

        return ResponseEntity.ok("Password reset successful. You can now log in.");
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshAccessToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        Optional<RefreshToken> optional = refreshTokenRepository.findByToken(refreshToken);

        if (optional.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid refresh token");
        }

        RefreshToken tokenObj = optional.get();

        if (tokenObj.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(tokenObj);
            return ResponseEntity.badRequest().body("Refresh token expired");
        }

        String newAccessToken = jwtUtil.GenerateToken(tokenObj.getUser().getUsername());
        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }

}
