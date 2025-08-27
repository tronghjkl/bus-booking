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

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository repo;

    @Override
    public RefreshToken create(User user, Duration ttl) {
        RefreshToken rt = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(LocalDateTime.now().plusSeconds(ttl.toSeconds()))
                .build();
        return repo.save(rt);
    }

    @Override public Optional<RefreshToken> findByToken(String token){ return repo.findByToken(token); }

    @Override public void deleteByUserId(Long userId){ repo.deleteByUserId(userId); }
}
