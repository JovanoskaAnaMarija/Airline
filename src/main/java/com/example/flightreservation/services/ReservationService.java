package com.example.flightreservation.services;

import com.example.flightreservation.domains.Reservation;
import com.example.flightreservation.dto.ReservationRequest;

public interface ReservationService {
    public Reservation bookFlight(ReservationRequest reservationRequest);
}
