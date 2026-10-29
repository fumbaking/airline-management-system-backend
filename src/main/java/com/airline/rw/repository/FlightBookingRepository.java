package com.airline.rw.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airline.rw.domain.FlightBooking;

@Repository
public interface FlightBookingRepository extends JpaRepository<FlightBooking, UUID> {

}
