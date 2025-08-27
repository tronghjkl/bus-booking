package com.example.bus_booking.repository;

import com.example.bus_booking.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByUserId(Long userId);

    List<Ticket> findByTripId(Long tripId);

    Optional<Ticket> findByTripIdAndSeatId(Long tripId, Long seatId);
}

