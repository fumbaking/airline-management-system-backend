package com.airline.rw.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.airline.rw.domain.CheckInAgent;
import com.airline.rw.domain.Counter;

@Repository
public interface CheckInAgentRepository extends JpaRepository<CheckInAgent, UUID> {

    Optional<CheckInAgent> findByCounter(Counter counter);

    boolean existsByCounter(Counter counter);
}
