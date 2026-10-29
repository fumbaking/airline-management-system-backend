package com.airline.rw.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.airline.rw.domain.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, UUID> {

    @Query("SELECT l FROM Language l WHERE l.agents IS EMPTY")
    List<Language> findUnusedLanguages();
}
