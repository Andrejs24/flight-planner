package io.codelex.flightplanner.flight.response;

import io.codelex.flightplanner.flight.domain.Airport;
import io.codelex.flightplanner.flight.domain.Flight;
import io.codelex.flightplanner.flight.domain.PageResult;
import io.codelex.flightplanner.flight.request.CreateFlightRequest;
import io.codelex.flightplanner.flight.request.SearchFlightRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }
    public ListFlightResponse listFlights() {
        return new ListFlightResponse(flightRepository.showSavedFlights());
    }


    public boolean isFlightExists(CreateFlightRequest request) {
        return flightRepository.isFlightExists(request.getFrom(), request.getTo(), request.getCarrier(), request.getArrivalTime(), request.getDepartureTime());
    }

    public synchronized Flight createFlight(@Valid CreateFlightRequest request) {
//
        if (!tripIsPossible(request)) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This is a problem");        }
        if (!dateIsCorrect(request)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date is not appropriated ");
        }
        if (!isFlightExists(request)) {
            long lastUsedId = flightRepository.showSavedFlights().stream().mapToLong(Flight::getId).max().orElse(0);
            Flight flight = new Flight(lastUsedId + 1, request.getFrom(), request.getTo(), request.getCarrier(), request.getArrivalTime(), request.getDepartureTime());
            flightRepository.saveFlights(flight);
            return flight;
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Flight already exists from " + request.getFrom() + " to " + request.getTo());
        }
    }


    public void clear() {
        flightRepository.clearFlights();
    }


    public boolean tripIsPossible(CreateFlightRequest request) {
        return !(request.getFrom()).getAirport().toLowerCase().trim().equals((request.getTo()).getAirport().toLowerCase().trim());
    }

    public boolean dateIsCorrect(CreateFlightRequest request) {
        return !request.getDepartureTime().isAfter(request.getArrivalTime()) && !request.getDepartureTime().equals(request.getArrivalTime());
    }

    public Flight searchFlightById(long id) {

        System.out.println("Saved Flights: " + flightRepository.showSavedFlights());

        Flight foundFlight = flightRepository.showSavedFlights().stream()
                .filter(flight -> flight.getId() == id)
                .findFirst()
                .orElse(null);
        System.out.println("Found Flight: " + foundFlight);
        return foundFlight;
    }

    public void deleteFlightById(long id) {
        flightRepository.deleteFlight(id);
    }

    public List<Airport> searchAirportsByPhrase(String phrase) {
        return flightRepository.showSavedFlights().stream()
                .flatMap(flight -> Stream.of(flight.getFrom(), flight.getTo()))
                .filter(airport -> airport.getAirport().toLowerCase().trim().startsWith(phrase.toLowerCase().trim())
                        || airport.getCity().toLowerCase().trim().startsWith(phrase.toLowerCase().trim())
                        || airport.getCountry().toLowerCase().trim().startsWith(phrase.toLowerCase().trim()))
                .toList();
    }


    public synchronized PageResult<Flight> searchFlights(@Valid SearchFlightRequest request) {
        if (request.getFrom().equals(request.getTo())) {
            throw new IllegalArgumentException("Can`t be same airports!");
        }

        List<Flight> flights = flightRepository.showSavedFlights().stream()
                .filter(flight -> flight.getFrom().getAirport().equals(request.getFrom()))
                .filter(flight -> flight.getTo().getAirport().equals(request.getTo()))
                .filter(flight -> flight.getDepartureTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                        .equals(request.getDepartureDate()))
                .toList();

        return new PageResult<>(0, flights.size(), flights);
    }

}

