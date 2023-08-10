package io.codelex.flightplanner.flight.response;

import io.codelex.flightplanner.flight.domain.Airport;
import io.codelex.flightplanner.flight.domain.Flight;
import io.codelex.flightplanner.flight.domain.PageResult;
import io.codelex.flightplanner.flight.request.CreateFlightRequest;
import io.codelex.flightplanner.flight.request.SearchFlightRequest;
import jakarta.validation.Valid;

import java.util.List;


public interface FlightService {

    public ListFlightResponse listFlights();

    public boolean isFlightExists(CreateFlightRequest request);

    public Flight createFlight(@Valid CreateFlightRequest request);

    public void clear();

    public boolean tripIsPossible(CreateFlightRequest request);

    public boolean dateIsCorrect(CreateFlightRequest request);

    public Flight searchFlightById(long id);

    public void deleteFlightById(long id);

    public PageResult<Flight> searchFlights(@Valid SearchFlightRequest request);

    public List<Airport> searchAirportsByPhrase(String phrase);

}
