package com.airline.rw.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airline.rw.domain.FlightBooking;
import com.airline.rw.domain.FlightBookingStatus;

@Repository
public interface FlightBookingRepository extends JpaRepository<FlightBooking, UUID> {

    List<FlightBooking> findByStatusOrderByFlightDateAsc(FlightBookingStatus status);

    List<FlightBooking> findByFlightDateBetweenOrderByFlightDateAsc(LocalDate start, LocalDate end);
}
