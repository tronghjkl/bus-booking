package com.example.bus_booking.repository;

import com.example.bus_booking.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByTicketId(Long ticketId);
}

