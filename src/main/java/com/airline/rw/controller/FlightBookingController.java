package com.airline.rw.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.airline.rw.domain.FlightBooking;
import com.airline.rw.domain.FlightBookingStatus;
import com.airline.rw.service.FlightBookingService;

@RestController
@RequestMapping("/api/flight-bookings")
public class FlightBookingController {

    @Autowired
    private FlightBookingService flightBookingService;

    @PostMapping("/save")
    public ResponseEntity<?> saveFlightBooking(@RequestBody FlightBooking flightBooking) {
        String message = flightBookingService.saveFlightBooking(flightBooking);
        if (message.equals("Flight booking saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        if (message.equals("Agent already has a flight booking on that date")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
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
        if (message.equals("That flight booking has baggage items, delete them first")) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<FlightBooking>> getByStatus(@RequestParam FlightBookingStatus status) {
        return new ResponseEntity<>(flightBookingService.getFlightBookingsByStatus(status), HttpStatus.OK);
    }

    @GetMapping("/between")
    public ResponseEntity<List<FlightBooking>> getBetween(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return new ResponseEntity<>(flightBookingService.getFlightBookingsBetween(startDate, endDate), HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> changeStatus(@PathVariable UUID id, @RequestParam FlightBookingStatus status) {
        String message = flightBookingService.changeStatus(id, status);
        if (message.equals("Flight booking status updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        if (message.equals("The flight booking with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/stats/by-status")
    public ResponseEntity<List<Object[]>> getStatsByStatus() {
        return new ResponseEntity<>(flightBookingService.countByStatus(), HttpStatus.OK);
    }

    @PatchMapping("/cancel-day")
    public ResponseEntity<?> cancelAgentDay(@RequestParam UUID checkInAgentId, @RequestParam String date) {
        String message = flightBookingService.cancelAgentDay(checkInAgentId, LocalDate.parse(date));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<org.springframework.data.domain.Page<FlightBooking>> getPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "flightDate,asc") String sort) {
        return new ResponseEntity<>(flightBookingService.getFlightBookingsPage(page, size, sort), HttpStatus.OK);
    }
}
