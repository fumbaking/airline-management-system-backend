package com.airline.rw.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airline.rw.domain.Language;
import com.airline.rw.service.LanguageService;

@RestController
@RequestMapping("/api/languages")
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @PostMapping("/save")
    public ResponseEntity<?> saveLanguage(@RequestBody Language language) {
        String message = languageService.saveLanguage(language);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Language>> getAllLanguages() {
        return new ResponseEntity<>(languageService.getAllLanguages(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLanguageById(@PathVariable UUID id) {
        return languageService.getLanguageById(id)
                .<ResponseEntity<?>>map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>("The language with that id does not exist", HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateLanguage(@PathVariable UUID id, @RequestBody Language language) {
        String message = languageService.updateLanguage(id, language);
        if (message.equals("Language updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLanguage(@PathVariable UUID id) {
        String message = languageService.deleteLanguage(id);
        if (message.equals("Language deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/unused")
    public ResponseEntity<List<Language>> getUnusedLanguages() {
        return new ResponseEntity<>(languageService.getUnusedLanguages(), HttpStatus.OK);
    }
}
