package com.airline.rw.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.airline.rw.domain.Passenger;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, UUID> {

    List<Passenger> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("SELECT DISTINCT fb.passenger FROM FlightBooking fb WHERE fb.agent.id = :agentId")
    List<Passenger> findPassengersOfAgent(@Param("agentId") UUID agentId);
}
