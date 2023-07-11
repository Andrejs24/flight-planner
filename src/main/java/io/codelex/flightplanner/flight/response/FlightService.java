package io.codelex.flightplanner.flight.response;

import io.codelex.flightplanner.flight.domain.Flight;
import io.codelex.flightplanner.flight.exeptions.DuplicateFlightException;
import io.codelex.flightplanner.flight.request.CreateFlightRequest;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;

@Service
public class FlightService {

    private FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public synchronized void saveFlight(CreateFlightRequest request) {

    }

    public ListFlightResponse listFlights() {
        return new ListFlightResponse(flightRepository.showSavedFlights());
    }

    public boolean isFlightExists(CreateFlightRequest request) {
        return flightRepository.isFlightExists(request.getFrom(), request.getTo(), request.getCarrier(), request.getArrivalTime(), request.getDepartureTime()) ? true : false;
    }

    public synchronized Flight createFlight(CreateFlightRequest request) {
        if (!valuesNotBlank(request)) {
            throw new NullPointerException("Should not be blank parameter");
        }
        if(!tripIsPossible(request)){
            throw new NullPointerException("Can`t fly to the same airport");
        }
        if (!dateIsCorrect(request)) {
            throw new DateTimeException("Dates are incorrect");
        }
        if (!isFlightExists(request)) {
            long lastUsedId = flightRepository.showSavedFlights().stream().mapToLong(c -> c.getId()).max().orElse(0);
            Flight flight = new Flight(lastUsedId + 1, request.getFrom(), request.getTo(), request.getCarrier(), request.getArrivalTime(), request.getDepartureTime());
            flightRepository.saveFlights(flight);
            return flight;
        } else {
            throw new DuplicateFlightException("Flight already exists from " + request.getFrom() + " to " + request.getTo());
        }
    }


    public void clear() {
        flightRepository.clearFlights();
    }

    public boolean valuesNotBlank(CreateFlightRequest request) {
        if (request.getTo() == null ||
                request.getFrom() == null ||
                (request.getCarrier() == null || request.getCarrier() == "") ||
                request.getArrivalTime() == null ||
                request.getDepartureTime() == null ||
                ((request.getTo()).getAirport() == null || request.getTo().getAirport() == "") ||
                ((request.getTo()).getCity() == null || request.getTo().getCity() == "") ||
                ((request.getTo()).getCountry() == null || request.getTo().getCountry() == "") ||
                ((request.getFrom()).getAirport() == null || request.getFrom().getAirport() == "") ||
                ((request.getFrom()).getCity() == null || request.getFrom().getCity() == "") ||
                ((request.getFrom()).getCountry() == null || request.getFrom().getCountry() == ""))
            return false;
        else return true;
    }

    public boolean tripIsPossible(CreateFlightRequest request){
        return ((request.getFrom()).getAirport().toLowerCase().trim().equals((request.getTo()).getAirport().toLowerCase().trim()))?false:true;
    }

    public boolean dateIsCorrect(CreateFlightRequest request){
        return (request.getDepartureTime().isAfter(request.getArrivalTime()) || request.getDepartureTime().equals(request.getArrivalTime())) ?false:true;
    }

}

