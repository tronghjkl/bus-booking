package com.example.bus_booking.jwt;

import com.example.bus_booking.jwt.dto.AuthResponse;
import com.example.bus_booking.jwt.dto.LoginRequest;
import com.example.bus_booking.jwt.dto.RegisterRequest;
import com.example.bus_booking.jwt.dto.TokenRefreshRequest;


public interface AuthService {
    AuthResponse register(RegisterRequest req);
    AuthResponse login(LoginRequest req);
    AuthResponse refresh(TokenRefreshRequest req);
    void logout(Long userId);
}

