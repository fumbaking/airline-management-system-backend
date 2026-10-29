package com.airline.rw.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airline.rw.domain.Passenger;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, UUID> {

}
