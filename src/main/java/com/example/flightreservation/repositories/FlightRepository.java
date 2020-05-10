package com.example.flightreservation.repositories;

import com.example.flightreservation.domains.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.ModelMap;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight,Long> {
    // JPQL query


    @Query("from Flight where departureCity=:departureCity and arrivalCity=:arrivalCity and dateOfDeparture=:dateOfDeparture")
    List<Flight> findFlights(@Param("departureCity") String from, @Param("arrivalCity") String to,
                             @Param("dateOfDeparture") Date departureDate);

}
