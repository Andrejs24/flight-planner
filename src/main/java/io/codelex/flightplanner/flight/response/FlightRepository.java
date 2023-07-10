package io.codelex.flightplanner.flight.response;

import io.codelex.flightplanner.flight.domain.Airport;
import io.codelex.flightplanner.flight.domain.Flight;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightRepository {

    private List<Flight> savedFlights = new ArrayList<>();

    public void saveFlights(Flight flight) {
        this.savedFlights.add(flight);
    }

    public List<Flight> showSavedFlights() {
        return savedFlights;
    }

    public void clearFlights() {
        savedFlights.clear();
    }

    public boolean isFlightExists(Airport from, Airport to) {
        return showSavedFlights().stream()
                .anyMatch(flight -> flight.getFrom().equals(from) && flight.getTo().equals(to));
    }

}
