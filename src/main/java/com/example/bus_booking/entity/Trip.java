package com.example.bus_booking.entity;

import com.example.bus_booking.enums.TripStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trip")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    private String departureLocation;
    private String arrivalLocation;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private TripStatus status;
}

