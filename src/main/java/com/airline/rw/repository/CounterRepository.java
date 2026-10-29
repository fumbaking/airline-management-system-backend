package com.airline.rw.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.airline.rw.domain.Counter;

@Repository
public interface CounterRepository extends JpaRepository<Counter, UUID> {

    Optional<Counter> findByCounterNumber(int counterNumber);

    @Query("SELECT c FROM Counter c WHERE NOT EXISTS (SELECT a FROM CheckInAgent a WHERE a.counter = c) ORDER BY c.counterNumber ASC")
    List<Counter> findFreeCounters();
}
