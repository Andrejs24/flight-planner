package io.codelex.flightplanner.flight.response;

import io.codelex.flightplanner.flight.domain.Flight;

import java.util.List;
import java.util.Objects;

public class ListFlightResponse {

    private List<Flight> flights;

    public ListFlightResponse(List<Flight> flights) {
        this.flights = flights;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListFlightResponse that)) return false;
        return Objects.equals(flights, that.flights);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flights);
    }

    @Override
    public String toString() {
        return "ListFlightResponse{" +
                "flights=" + flights +
                '}';
    }
}
