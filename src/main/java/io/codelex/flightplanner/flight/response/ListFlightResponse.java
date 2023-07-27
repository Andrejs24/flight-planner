package io.codelex.flightplanner.flight.response;

import io.codelex.flightplanner.flight.domain.Flight;

import java.util.List;

public class ListFlightResponse {

    List<Flight> flights;

    public ListFlightResponse(List<Flight> flights) {
        this.flights = flights;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }
}
