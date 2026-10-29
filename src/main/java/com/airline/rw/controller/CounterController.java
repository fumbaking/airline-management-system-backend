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

import com.airline.rw.domain.Counter;
import com.airline.rw.service.CounterService;

@RestController
@RequestMapping("/api/counters")
public class CounterController {

    @Autowired
    private CounterService counterService;

    @PostMapping("/save")
    public ResponseEntity<?> saveCounter(@RequestBody Counter counter) {
        String message = counterService.saveCounter(counter);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Counter>> getAllCounters() {
        return new ResponseEntity<>(counterService.getAllCounters(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCounterById(@PathVariable UUID id) {
        return counterService.getCounterById(id)
                .<ResponseEntity<?>>map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                .orElse(new ResponseEntity<>("The counter with that id does not exist", HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCounter(@PathVariable UUID id, @RequestBody Counter counter) {
        String message = counterService.updateCounter(id, counter);
        if (message.equals("Counter updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCounter(@PathVariable UUID id) {
        String message = counterService.deleteCounter(id);
        if (message.equals("Counter deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/free")
    public ResponseEntity<List<Counter>> getFreeCounters() {
        return new ResponseEntity<>(counterService.getFreeCounters(), HttpStatus.OK);
    }
}
