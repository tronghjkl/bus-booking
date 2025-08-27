package com.example.bus_booking.entity;

import com.example.bus_booking.enums.BusStatus;
import com.example.bus_booking.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "bus")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String licensePlate;

    @Column(nullable = false)
    private String busType;

    private int seatCount;

    @Enumerated(EnumType.STRING)
    private BusStatus status;
}

