package io.codelex.flightplanner.flight.response;

import io.codelex.flightplanner.flight.domain.Flight;
import io.codelex.flightplanner.flight.request.CreateFlightRequest;
import org.springframework.stereotype.Service;

@Service
public class FlightService {

    private FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public synchronized void saveFlight(CreateFlightRequest request) {

    }

    public ListFlightResponse listFlights(){
        return new ListFlightResponse(flightRepository.showSavedFlights());
    }

    public Flight addFlight(CreateFlightRequest request){
        long lastUsedId = flightRepository.showSavedFlights().stream().mapToLong(c -> c.getId()).max().orElse(0);
        Flight flight = new Flight(lastUsedId + 1,request.getFrom(), request.getTo(), request.getCarrier(),request.getArrivalTime(),request.getDepartureTime());
        flightRepository.saveFlights(flight);
        return flight;
    }

    public void clear(){
       flightRepository.clearFlights();
    }

}

