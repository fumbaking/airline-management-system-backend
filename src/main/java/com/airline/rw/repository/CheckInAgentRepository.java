package com.airline.rw.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.airline.rw.domain.CheckInAgent;
import com.airline.rw.domain.Counter;

@Repository
public interface CheckInAgentRepository extends JpaRepository<CheckInAgent, UUID> {

    boolean existsByCounter(Counter counter);

    @Query("SELECT a FROM CheckInAgent a JOIN a.languages l WHERE LOWER(l.name) = LOWER(:name)")
    List<CheckInAgent> findByLanguageName(@Param("name") String name);
}
