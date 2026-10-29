package com.airline.rw.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airline.rw.domain.Counter;

@Repository
public interface CounterRepository extends JpaRepository<Counter, UUID> {

    Optional<Counter> findByCounterNumber(int counterNumber);
}
