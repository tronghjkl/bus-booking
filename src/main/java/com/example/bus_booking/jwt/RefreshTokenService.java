package com.example.bus_booking.jwt;

import com.example.bus_booking.entity.RefreshToken;
import com.example.bus_booking.entity.User;
import com.example.bus_booking.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

// service/RefreshTokenService.java
public interface RefreshTokenService {
    RefreshToken create(User user, Duration ttl);
    Optional<RefreshToken> findByToken(String token);
    void deleteByUserId(Long userId);
}

