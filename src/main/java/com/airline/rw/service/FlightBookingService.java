package com.airline.rw.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.airline.rw.domain.FlightBooking;
import com.airline.rw.domain.FlightBookingStatus;
import com.airline.rw.repository.BaggageItemRepository;
import com.airline.rw.repository.FlightBookingRepository;

@Service
public class FlightBookingService {

    @Autowired
    private FlightBookingRepository flightBookingRepo;

    @Autowired
    private BaggageItemRepository baggageItemRepo;

    public String saveFlightBooking(FlightBooking flightBooking) {
        if (flightBooking.getFlightDate().isBefore(LocalDate.now())) {
            return "Flight booking date cannot be in the past";
        }
        if (flightBooking.getAgent() != null && flightBooking.getAgent().getId() != null) {
            boolean conflict = flightBookingRepo.existsByAgentIdAndFlightDateAndStatusNot(
                    flightBooking.getAgent().getId(),
                    flightBooking.getFlightDate(),
                    FlightBookingStatus.CANCELLED);
            if (conflict) {
                return "Agent already has a flight booking on that date";
            }
        }
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
        Optional<FlightBooking> existing = flightBookingRepo.findById(id);
        if (existing.isEmpty()) {
            return "The flight booking with that id does not exist";
        }
        if (baggageItemRepo.existsByFlightBooking(existing.get())) {
            return "That flight booking has baggage items, delete them first";
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

    public String changeStatus(UUID id, FlightBookingStatus newStatus) {
        Optional<FlightBooking> existing = flightBookingRepo.findById(id);
        if (existing.isEmpty()) {
            return "The flight booking with that id does not exist";
        }
        FlightBookingStatus current = existing.get().getStatus();
        boolean allowed =
                (current == FlightBookingStatus.BOOKED &&
                        (newStatus == FlightBookingStatus.PAID || newStatus == FlightBookingStatus.CANCELLED))
                ||
                (current == FlightBookingStatus.PAID &&
                        (newStatus == FlightBookingStatus.CHECKED_IN || newStatus == FlightBookingStatus.CANCELLED));
        if (!allowed) {
            return "Cannot change status from " + current + " to " + newStatus;
        }
        existing.get().setStatus(newStatus);
        flightBookingRepo.save(existing.get());
        return "Flight booking status updated successfully";
    }

    public List<Object[]> countByStatus() {
        return flightBookingRepo.countByStatus();
    }

    @Transactional
    public String cancelAgentDay(UUID agentId, LocalDate date) {
        int cancelled = flightBookingRepo.cancelAgentDay(agentId, date);
        return cancelled + " flight bookings cancelled";
    }

    public Page<FlightBooking> getFlightBookingsPage(int page, int size, String sort) {
        String[] parts = sort.split(",");
        String field = parts[0];
        Sort.Direction direction = parts.length > 1 && parts[1].equalsIgnoreCase("desc")
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, field));
        return flightBookingRepo.findAll(pageable);
    }
}
