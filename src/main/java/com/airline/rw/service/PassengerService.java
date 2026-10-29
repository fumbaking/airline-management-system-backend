package com.airline.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airline.rw.domain.Passenger;
import com.airline.rw.repository.PassengerRepository;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepo;

    public String savePassenger(Passenger passenger) {
        passengerRepo.save(passenger);
        return "Passenger saved successfully";
    }

    public List<Passenger> getAllPassengers() {
        return passengerRepo.findAll();
    }

    public Optional<Passenger> getPassengerById(UUID id) {
        return passengerRepo.findById(id);
    }

    public String updatePassenger(UUID id, Passenger updated) {
        Optional<Passenger> existing = passengerRepo.findById(id);
        if (existing.isEmpty()) {
            return "The passenger with that id does not exist";
        }
        Passenger passenger = existing.get();
        passenger.setFirstName(updated.getFirstName());
        passenger.setLastName(updated.getLastName());
        passenger.setPassportNumber(updated.getPassportNumber());
        passenger.setEmail(updated.getEmail());
        passenger.setPhone(updated.getPhone());
        passengerRepo.save(passenger);
        return "Passenger updated successfully";
    }

    public String deletePassenger(UUID id) {
        if (passengerRepo.findById(id).isEmpty()) {
            return "The passenger with that id does not exist";
        }
        passengerRepo.deleteById(id);
        return "Passenger deleted successfully";
    }

    public List<Passenger> getPassengersByLastName(String lastName) {
        return passengerRepo.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }
}
