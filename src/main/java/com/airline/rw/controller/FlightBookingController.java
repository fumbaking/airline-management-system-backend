package com.airline.rw.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airline.rw.domain.FlightBooking;
import com.airline.rw.service.FlightBookingService;

@RestController
@RequestMapping("/api/flight-bookings")
public class FlightBookingController {

    @Autowired
    private FlightBookingService flightBookingService;

    @PostMapping("/save")
    public ResponseEntity<?> saveFlightBooking(@RequestBody FlightBooking flightBooking) {
        String message = flightBookingService.saveFlightBooking(flightBooking);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<FlightBooking>> getAllFlightBookings() {
        return new ResponseEntity<>(flightBookingService.getAllFlightBookings(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFlightBookingById(@PathVariable UUID id) {
        return flightBookingService.getFlightBookingById(id)
                .<ResponseEntity<?>>map(fb -> new ResponseEntity<>(fb, HttpStatus.OK))
                .orElse(new ResponseEntity<>("The flight booking with that id does not exist", HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateFlightBooking(@PathVariable UUID id, @RequestBody FlightBooking flightBooking) {
        String message = flightBookingService.updateFlightBooking(id, flightBooking);
        if (message.equals("Flight booking updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFlightBooking(@PathVariable UUID id) {
        String message = flightBookingService.deleteFlightBooking(id);
        if (message.equals("Flight booking deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
