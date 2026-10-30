package com.airline.rw.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airline.rw.domain.BaggageItem;
import com.airline.rw.domain.FlightBooking;
import com.airline.rw.domain.FlightBookingStatus;
import com.airline.rw.repository.BaggageItemRepository;
import com.airline.rw.repository.FlightBookingRepository;

@Service
public class BaggageItemService {

    @Autowired
    private BaggageItemRepository baggageItemRepo;

    @Autowired
    private FlightBookingRepository flightBookingRepo;

    public String saveBaggageItem(UUID flightBookingId, BaggageItem baggageItem) {
        Optional<FlightBooking> bookingOpt = flightBookingRepo.findById(flightBookingId);
        if (bookingOpt.isEmpty()) {
            return "The flight booking with that id does not exist";
        }
        if (bookingOpt.get().getStatus() != FlightBookingStatus.CHECKED_IN) {
            return "Baggage items are only for CHECKED_IN flight bookings";
        }
        baggageItem.setCheckedDate(LocalDate.now());
        baggageItem.setFlightBooking(bookingOpt.get());
        baggageItemRepo.save(baggageItem);
        return "Baggage item saved successfully";
    }

    public List<BaggageItem> getByPassengerPassportNumber(String passportNumber) {
        return baggageItemRepo.findByPassengerPassportNumber(passportNumber);
    }

    public String deleteBaggageItem(UUID id) {
        if (baggageItemRepo.findById(id).isEmpty()) {
            return "The baggage item with that id does not exist";
        }
        baggageItemRepo.deleteById(id);
        return "Baggage item deleted successfully";
    }
}
