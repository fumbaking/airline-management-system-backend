package com.airline.rw.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.airline.rw.domain.FlightBooking;
import com.airline.rw.domain.FlightBookingStatus;

@Repository
public interface FlightBookingRepository extends JpaRepository<FlightBooking, UUID> {

    List<FlightBooking> findByStatusOrderByFlightDateAsc(FlightBookingStatus status);

    List<FlightBooking> findByFlightDateBetweenOrderByFlightDateAsc(LocalDate start, LocalDate end);

    boolean existsByAgentIdAndFlightDateAndStatusNot(UUID agentId, LocalDate flightDate, FlightBookingStatus status);

    long countByStatus(FlightBookingStatus status);

    long countByFlightDate(LocalDate date);

    @Query("SELECT fb.status, COUNT(fb) FROM FlightBooking fb GROUP BY fb.status")
    List<Object[]> countByStatus();

    @Modifying
    @Query("UPDATE FlightBooking fb SET fb.status = com.airline.rw.domain.FlightBookingStatus.CANCELLED WHERE fb.agent.id = :agentId AND fb.flightDate = :date AND fb.status <> com.airline.rw.domain.FlightBookingStatus.CHECKED_IN")
    int cancelAgentDay(@Param("agentId") UUID agentId, @Param("date") LocalDate date);

    Page<FlightBooking> findAll(Pageable pageable);
}
