package com.example.bus_booking.jwt;

import com.example.bus_booking.entity.RefreshToken;
import com.example.bus_booking.entity.User;
import com.example.bus_booking.enums.Role;
import com.example.bus_booking.jwt.dto.AuthResponse;
import com.example.bus_booking.jwt.dto.LoginRequest;
import com.example.bus_booking.jwt.dto.RegisterRequest;
import com.example.bus_booking.jwt.dto.TokenRefreshRequest;
import com.example.bus_booking.jwt.service.CustomUserDetails;
import com.example.bus_booking.jwt.service.UserDetailsServiceImpl;
import com.example.bus_booking.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;
    private final Environment env;

    private Duration refreshTtl() {
        long ms = Long.parseLong(env.getProperty("app.jwt.refresh-expiration-ms","604800000"));
        return Duration.ofMillis(ms);
    }

    @Override
    public AuthResponse register(RegisterRequest req) {
        if (userRepo.existsByUsername(req.getUsername()))
            throw new IllegalArgumentException("Username already exists");
        if (userRepo.existsByEmail(req.getEmail()))
            throw new IllegalArgumentException("Email already exists");

        User user = User.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .password(encoder.encode(req.getPassword()))
                .fullName(req.getFullName())
                .phone(req.getPhone())
                .role(Role.CUSTOMER)
                .build();
        userRepo.save(user);

        UserDetails ud = new CustomUserDetails(user);
        String access = jwtService.generateAccessToken(ud);
        RefreshToken rt = refreshTokenService.create(user, refreshTtl());

        return AuthResponse.builder()
                .accessToken(access).refreshToken(rt.getToken())
                .tokenType("Bearer")
                .userId(user.getId()).username(user.getUsername()).role(user.getRole().name())
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );
        User user = userRepo.findByUsername(req.getUsername()).orElseThrow();
        UserDetails ud = new CustomUserDetails(user);

        String access = jwtService.generateAccessToken(ud);
        RefreshToken rt = refreshTokenService.create(user, refreshTtl());

        return AuthResponse.builder()
                .accessToken(access).refreshToken(rt.getToken())
                .tokenType("Bearer")
                .userId(user.getId()).username(user.getUsername()).role(user.getRole().name())
                .build();
    }

    @Override
    public AuthResponse refresh(TokenRefreshRequest req) {
        RefreshToken rt = refreshTokenService.findByToken(req.getRefreshToken())
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

        if (rt.getExpiryDate().isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("Refresh token expired");

        User user = rt.getUser();
        UserDetails ud = new CustomUserDetails(user);

        String access = jwtService.generateAccessToken(ud);

        // (tuỳ chọn) rotate refresh token:
        refreshTokenService.deleteByUserId(user.getId());
        RefreshToken newRt = refreshTokenService.create(user, refreshTtl());

        return AuthResponse.builder()
                .accessToken(access).refreshToken(newRt.getToken())
                .tokenType("Bearer")
                .userId(user.getId()).username(user.getUsername()).role(user.getRole().name())
                .build();
    }

    @Override
    public void logout(Long userId) {
        refreshTokenService.deleteByUserId(userId);
    }
}
