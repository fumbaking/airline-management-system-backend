package com.airline.rw.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airline.rw.domain.FlightBooking;
import com.airline.rw.domain.FlightBookingStatus;
import com.airline.rw.repository.FlightBookingRepository;

@Service
public class FlightBookingService {

    @Autowired
    private FlightBookingRepository flightBookingRepo;

    public String saveFlightBooking(FlightBooking flightBooking) {
        flightBooking.setStatus(FlightBookingStatus.BOOKED);
        flightBookingRepo.save(flightBooking);
        return "Flight booking saved successfully";
    }

    public List<FlightBooking> getAllFlightBookings() {
        return flightBookingRepo.findAll();
    }

    public Optional<FlightBooking> getFlightBookingById(UUID id) {
        return flightBookingRepo.findById(id);
    }

    public String updateFlightBooking(UUID id, FlightBooking updated) {
        Optional<FlightBooking> existing = flightBookingRepo.findById(id);
        if (existing.isEmpty()) {
            return "The flight booking with that id does not exist";
        }
        FlightBooking flightBooking = existing.get();
        flightBooking.setFlightDate(updated.getFlightDate());
        flightBooking.setFlightNumber(updated.getFlightNumber());
        flightBooking.setSeatClass(updated.getSeatClass());
        flightBooking.setPassenger(updated.getPassenger());
        flightBooking.setAgent(updated.getAgent());
        flightBookingRepo.save(flightBooking);
        return "Flight booking updated successfully";
    }

    public String deleteFlightBooking(UUID id) {
        if (flightBookingRepo.findById(id).isEmpty()) {
            return "The flight booking with that id does not exist";
        }
        flightBookingRepo.deleteById(id);
        return "Flight booking deleted successfully";
    }

    public List<FlightBooking> getFlightBookingsByStatus(FlightBookingStatus status) {
        return flightBookingRepo.findByStatusOrderByFlightDateAsc(status);
    }

    public List<FlightBooking> getFlightBookingsBetween(LocalDate start, LocalDate end) {
        return flightBookingRepo.findByFlightDateBetweenOrderByFlightDateAsc(start, end);
    }
}
