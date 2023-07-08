package io.codelex.flightplanner.flight.domain;

import java.time.LocalDateTime;

public class Flight {
    private String arrival;
    private String destination;
    private long id;
    private LocalDateTime arrivalDateAndTime;
    private LocalDateTime departureDateAndTime;

    public Flight(String arrival, String destination, long id, LocalDateTime arrivalDateAndTime, LocalDateTime departureDateAndTime) {
        this.arrival = arrival;
        this.destination = destination;
        this.id = id;
        this.arrivalDateAndTime = arrivalDateAndTime;
        this.departureDateAndTime = departureDateAndTime;
    }


    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getArrivalDateAndTime() {
        return arrivalDateAndTime;
    }

    public void setArrivalDateAndTime(LocalDateTime arrivalDateAndTime) {
        this.arrivalDateAndTime = arrivalDateAndTime;
    }

    public LocalDateTime getDepartureDateAndTime() {
        return departureDateAndTime;
    }

    public void setDepartureDateAndTime(LocalDateTime departureDateAndTime) {
        this.departureDateAndTime = departureDateAndTime;
    }
}
