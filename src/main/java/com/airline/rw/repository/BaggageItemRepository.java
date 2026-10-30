package com.airline.rw.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.airline.rw.domain.BaggageItem;
import com.airline.rw.domain.FlightBooking;

@Repository
public interface BaggageItemRepository extends JpaRepository<BaggageItem, UUID> {

    boolean existsByFlightBooking(FlightBooking flightBooking);

    @Query("SELECT b FROM BaggageItem b WHERE b.flightBooking.passenger.passportNumber = :passportNumber ORDER BY b.checkedDate DESC")
    List<BaggageItem> findByPassengerPassportNumber(@Param("passportNumber") String passportNumber);
}
