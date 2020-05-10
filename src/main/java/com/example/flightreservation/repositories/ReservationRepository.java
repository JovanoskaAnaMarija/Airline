package com.example.flightreservation.repositories;

import com.example.flightreservation.domains.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
}
