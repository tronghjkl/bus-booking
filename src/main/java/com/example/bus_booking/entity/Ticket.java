package com.example.bus_booking.entity;
import com.example.bus_booking.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Table(name = "ticket", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"trip_id", "seat_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime bookingTime;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @PrePersist
    public void prePersist() {
        bookingTime = LocalDateTime.now();
    }
}

