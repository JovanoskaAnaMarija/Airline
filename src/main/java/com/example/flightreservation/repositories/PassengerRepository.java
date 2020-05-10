package com.example.flightreservation.repositories;

import com.example.flightreservation.domains.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger,Long> {
}

