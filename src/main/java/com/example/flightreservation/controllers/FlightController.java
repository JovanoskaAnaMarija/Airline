package com.example.flightreservation.controllers;

import com.example.flightreservation.domains.Flight;
import com.example.flightreservation.repositories.FlightRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Controller
public class FlightController {

    private static final Logger LOGGER= LoggerFactory.getLogger(FlightController.class);

    @Autowired
    FlightRepository flightRepository;

    @RequestMapping("/findFlights")
    public String findFLights(@RequestParam("source") String source, @RequestParam("destination") String destination,
                              @RequestParam("departDate") @DateTimeFormat(pattern = "MM-dd-yyyy") Date departDate, ModelMap modelMap) {

        LOGGER.info("Inside findFlights() From:" + source + " TO:" + destination + "Departure Date: " + departDate);
        List<Flight> flights =flightRepository.findFlights(source,destination,departDate);
        modelMap.addAttribute("flights",flights);
        LOGGER.info("Flights found are"+flights.toString());
        return "flights/displayFlights";
    }

    @RequestMapping("/admin/showAddFlight")
    public String showAddFlightPage(){
        return "flights/addFlight";
    }

    @RequestMapping(value = "/admin/addFlight")
    public String addFlight(@ModelAttribute("flight") Flight flight, ModelMap modelmap){
            flightRepository.save(flight);
            modelmap.addAttribute("msg","Flight Added Successfully");
        return "flights/addFlight";
    }

    @RequestMapping("/showAllFlights")
    public String findAllFlights(Model modelMap)
    {
        List<Flight> flights = flightRepository.findAll();
        modelMap.addAttribute("flights", flights);
        return "flights/allFlights";
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}/deleteFlight", method = RequestMethod.POST)
    public String deleteFlightnWithPost(@PathVariable Long id) {
        this.flightRepository.deleteById(id);
        return "redirect:/showAllFlights";
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}/deleteFlight")
    public String deleteFlightWithDelete(@PathVariable Long id) {
        this.flightRepository.deleteById(id);
        return "flights/allFlights";
    }




}

