package com.example.bus_booking.repository;

import com.example.bus_booking.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByDepartureLocationAndArrivalLocation(String departure, String arrival);

    List<Trip> findByDepartureLocationAndArrivalLocationAndDepartureTimeBetween(
            String departure, String arrival,
            LocalDateTime start, LocalDateTime end
    );
}

