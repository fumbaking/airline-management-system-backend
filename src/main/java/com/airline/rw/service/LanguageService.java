package com.airline.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airline.rw.domain.Language;
import com.airline.rw.repository.LanguageRepository;

@Service
public class LanguageService {

    @Autowired
    private LanguageRepository languageRepo;

    public String saveLanguage(Language language) {
        languageRepo.save(language);
        return "Language saved successfully";
    }

    public List<Language> getAllLanguages() {
        return languageRepo.findAll();
    }

    public Optional<Language> getLanguageById(UUID id) {
        return languageRepo.findById(id);
    }

    public String updateLanguage(UUID id, Language updated) {
        Optional<Language> existing = languageRepo.findById(id);
        if (existing.isEmpty()) {
            return "The language with that id does not exist";
        }
        Language language = existing.get();
        language.setName(updated.getName());
        languageRepo.save(language);
        return "Language updated successfully";
    }

    public String deleteLanguage(UUID id) {
        if (languageRepo.findById(id).isEmpty()) {
            return "The language with that id does not exist";
        }
        languageRepo.deleteById(id);
        return "Language deleted successfully";
    }
}
