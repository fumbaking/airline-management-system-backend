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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.airline.rw.domain.BaggageItem;
import com.airline.rw.service.BaggageItemService;

@RestController
@RequestMapping("/api/baggage-items")
public class BaggageItemController {

    @Autowired
    private BaggageItemService baggageItemService;

    @PostMapping("/save")
    public ResponseEntity<?> saveBaggageItem(@RequestParam UUID flightBookingId, @RequestBody BaggageItem baggageItem) {
        String message = baggageItemService.saveBaggageItem(flightBookingId, baggageItem);
        if (message.equals("Baggage item saved successfully")) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        if (message.equals("The flight booking with that id does not exist")) {
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/passenger/{passportNumber}")
    public ResponseEntity<List<BaggageItem>> getByPassengerPassportNumber(@PathVariable String passportNumber) {
        return new ResponseEntity<>(baggageItemService.getByPassengerPassportNumber(passportNumber), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBaggageItem(@PathVariable UUID id) {
        String message = baggageItemService.deleteBaggageItem(id);
        if (message.equals("Baggage item deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
