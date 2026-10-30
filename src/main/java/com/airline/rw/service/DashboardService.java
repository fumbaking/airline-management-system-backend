package com.airline.rw.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airline.rw.domain.FlightBookingStatus;
import com.airline.rw.repository.BaggageItemRepository;
import com.airline.rw.repository.CheckInAgentRepository;
import com.airline.rw.repository.CounterRepository;
import com.airline.rw.repository.FlightBookingRepository;
import com.airline.rw.repository.PassengerRepository;

@Service
public class DashboardService {

    @Autowired
    private PassengerRepository passengerRepo;

    @Autowired
    private CheckInAgentRepository agentRepo;

    @Autowired
    private CounterRepository counterRepo;

    @Autowired
    private FlightBookingRepository flightBookingRepo;

    @Autowired
    private BaggageItemRepository baggageItemRepo;

    public Map<String, Long> getSummary() {
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("passengers", passengerRepo.count());
        summary.put("agents", agentRepo.count());
        summary.put("freeCounters", (long) counterRepo.findFreeCounters().size());
        summary.put("bookingsToday", flightBookingRepo.countByFlightDate(LocalDate.now()));
        summary.put("booked", flightBookingRepo.countByStatus(FlightBookingStatus.BOOKED));
        summary.put("baggageItems", baggageItemRepo.count());
        return summary;
    }
}
