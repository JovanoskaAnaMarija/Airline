package com.example.flightreservation.services;

import com.example.flightreservation.domains.Flight;
import com.example.flightreservation.domains.Passenger;
import com.example.flightreservation.domains.Reservation;
import com.example.flightreservation.dto.ReservationRequest;
import com.example.flightreservation.exceptions.FlightNotFound;
import com.example.flightreservation.repositories.FlightRepository;
import com.example.flightreservation.repositories.PassengerRepository;
import com.example.flightreservation.repositories.ReservationRepository;
import com.example.flightreservation.util.EmailUtil;
import com.example.flightreservation.util.PdfGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    @Value("C:\\Users\\user\\Desktop\\reservation")
    private String ITINERARY_DIR;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PdfGenerator pdfGenerator;

    @Autowired
    private EmailUtil emailUtil;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceImpl.class);


    @Override
    public Reservation bookFlight(ReservationRequest reservationRequest) {
        // make payment here
        // if the payment is successful proceed..
        LOGGER.info("Inside bookFlight()");
        Long flightId=reservationRequest.getFlightId();
        Optional<Flight> flightOptional=flightRepository.findById(flightId);
        if (!flightOptional.isPresent()) {
            throw new FlightNotFound("No flight found with id "+flightId);
        }
        LOGGER.info("Flight found with id: {}",flightId);
        Flight flight=flightOptional.get();
        Passenger passenger=new Passenger();
        passenger.setFirstName(reservationRequest.getPassengerFirstName());
        passenger.setMiddleName(reservationRequest.getPassengerMiddleName());
        passenger.setLastName(reservationRequest.getPassengerLastName());
        passenger.setEmail(reservationRequest.getPassengerEmail());
        passenger.setPhone(reservationRequest.getPassengerPhone());

        passengerRepository.save(passenger);
        LOGGER.info("Saved the passenger:" + passenger);

        Reservation reservation=new Reservation();
        reservation.setFlight(flight);
        reservation.setPassenger(passenger);
        reservation.setCheckedin(false);
        final Reservation savedReservation = reservationRepository.save(reservation);
        LOGGER.info("Saving the reservation:" + reservation);


        String filePath = ITINERARY_DIR + savedReservation.getId()
                + ".pdf";
        LOGGER.info("Generating  the itinerary");
        pdfGenerator.generateItenary(savedReservation,filePath);
        LOGGER.info("Emailing the Itinerary");
        emailUtil.sendItenary("anamarijajovanoska0@gmail.com",filePath);

        return savedReservation;

    }
}
