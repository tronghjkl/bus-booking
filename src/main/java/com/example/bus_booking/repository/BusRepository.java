package com.example.bus_booking.repository;

import com.example.bus_booking.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusRepository extends JpaRepository<Bus, Long> {
    Optional<Bus> findByLicensePlate(String licensePlate);
}

