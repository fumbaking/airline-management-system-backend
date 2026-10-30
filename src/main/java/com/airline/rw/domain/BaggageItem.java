package com.airline.rw.domain;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "baggage_item")
public class BaggageItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "baggage_type")
    private String baggageType;

    private String description;

    @Column(name = "weight_kg")
    private int weightKg;

    @Column(name = "checked_date")
    private LocalDate checkedDate;

    @ManyToOne
    @JoinColumn(name = "flight_booking_id")
    private FlightBooking flightBooking;

    public UUID getId() { return id; }

    public String getBaggageType() { return baggageType; }
    public void setBaggageType(String baggageType) { this.baggageType = baggageType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getWeightKg() { return weightKg; }
    public void setWeightKg(int weightKg) { this.weightKg = weightKg; }

    public LocalDate getCheckedDate() { return checkedDate; }
    public void setCheckedDate(LocalDate checkedDate) { this.checkedDate = checkedDate; }

    public FlightBooking getFlightBooking() { return flightBooking; }
    public void setFlightBooking(FlightBooking flightBooking) { this.flightBooking = flightBooking; }
}
