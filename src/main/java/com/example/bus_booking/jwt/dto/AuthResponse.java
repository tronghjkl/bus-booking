package com.example.bus_booking.jwt.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType; // "Bearer"
    private Long userId;
    private String username;
    private String role;
}
