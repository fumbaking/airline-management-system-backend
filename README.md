Airline Management System Backend

Student: Fumba Huriro
Student ID: 25401
Course: INSY 8322 Web Technology and Internet
University: Adventist University of Central Africa

Project Overview

This is the backend for the Airline Management System midterm project.
It is built with Spring Boot, Spring Data JPA, and PostgreSQL.
The API manages passengers, check-in agents, counters, languages, flight bookings, and baggage items.

Prerequisites

Java 21
Maven 3.9 or higher
PostgreSQL 18

Database Setup

Start PostgreSQL then create the database by running this SQL command:

CREATE DATABASE airline_db;

The application connects on port 1122.
If your PostgreSQL runs on a different port, update the datasource URL in the configuration file.

Configuration

The configuration file is located at:
src/main/resources/application.properties

The default settings are:

spring.datasource.url=jdbc:postgresql://localhost:1122/airline_db
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.port=8080

How to Run

Open a terminal in the project folder and run:

mvn spring-boot:run

The API will be available at http://localhost:8080

API Endpoints

Languages
POST   /api/languages/save
GET    /api/languages/all
GET    /api/languages/{id}
PUT    /api/languages/update/{id}
DELETE /api/languages/delete/{id}
GET    /api/languages/unused

Counters
POST   /api/counters/save
GET    /api/counters/all
GET    /api/counters/{id}
PUT    /api/counters/update/{id}
DELETE /api/counters/delete/{id}
GET    /api/counters/free

Check-in Agents
POST   /api/check-in-agents/save
GET    /api/check-in-agents/all
GET    /api/check-in-agents/{id}
PUT    /api/check-in-agents/update/{id}
DELETE /api/check-in-agents/delete/{id}
GET    /api/check-in-agents/by-language?name=
PUT    /api/check-in-agents/{id}/languages/{languageId}
PATCH  /api/check-in-agents/{id}/counter?counterNumber=

Passengers
POST   /api/passengers/save
GET    /api/passengers/all
GET    /api/passengers/{id}
PUT    /api/passengers/update/{id}
DELETE /api/passengers/delete/{id}
GET    /api/passengers/by-last-name?lastName=
GET    /api/passengers/of-check-in-agent/{checkInAgentId}

Flight Bookings
POST   /api/flight-bookings/save
GET    /api/flight-bookings/all
GET    /api/flight-bookings/{id}
PUT    /api/flight-bookings/update/{id}
DELETE /api/flight-bookings/delete/{id}
GET    /api/flight-bookings/by-status?status=
GET    /api/flight-bookings/between?start=&end=
PATCH  /api/flight-bookings/{id}/status?status=
GET    /api/flight-bookings/stats/by-status
PATCH  /api/flight-bookings/cancel-day?checkInAgentId=&date=

Baggage Items
POST   /api/baggage-items/save?flightBookingId=
GET    /api/baggage-items/passenger/{passportNumber}
DELETE /api/baggage-items/delete/{id}

Postman Collection

Import the file airline-management-system-midterm.postman_collection.json into Postman.
No environment variables are needed. All IDs are real values from the database.
The collection contains requests for all parts A through F.

To load seed data, import seed.postman_collection.json and run it using the Collection Runner.
