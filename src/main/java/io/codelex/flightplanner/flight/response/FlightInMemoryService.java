package io.codelex.flightplanner.flight.response;

import io.codelex.flightplanner.flight.domain.Airport;
import io.codelex.flightplanner.flight.domain.Flight;
import io.codelex.flightplanner.flight.domain.PageResult;
import io.codelex.flightplanner.flight.request.CreateFlightRequest;
import io.codelex.flightplanner.flight.request.SearchFlightRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;


public class FlightInMemoryService implements FlightService {

    private final FlightInMemoryRepository flightInMemoryRepository;

    public FlightInMemoryService(FlightInMemoryRepository flightInMemoryRepository) {
        this.flightInMemoryRepository = flightInMemoryRepository;
    }

    public ListFlightResponse listFlights() {
        return new ListFlightResponse(flightInMemoryRepository.showSavedFlights());
    }


    public boolean isFlightExists(CreateFlightRequest request) {
        return flightInMemoryRepository.isFlightExists(request.getFrom(), request.getTo(), request.getCarrier(), request.getArrivalTime(), request.getDepartureTime());
    }

    public synchronized Flight createFlight(@Valid CreateFlightRequest request) {
//
        if (!tripIsPossible(request)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This is a problem");
        }
        if (!dateIsCorrect(request)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date is not appropriated ");
        }
        if (!isFlightExists(request)) {
            long lastUsedId = flightInMemoryRepository.showSavedFlights().stream().mapToLong(Flight::getId).max().orElse(0);
            Flight flight = new Flight(lastUsedId + 1, request.getFrom(), request.getTo(), request.getCarrier(), request.getArrivalTime(), request.getDepartureTime());
            flightInMemoryRepository.saveFlights(flight);
            return flight;
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Flight already exists from " + request.getFrom() + " to " + request.getTo());
        }
    }


    public void clear() {
        flightInMemoryRepository.clearFlights();
    }


    public boolean tripIsPossible(CreateFlightRequest request) {
        return !(request.getFrom()).getAirport().toLowerCase().trim().equals((request.getTo()).getAirport().toLowerCase().trim());
    }

    public boolean dateIsCorrect(CreateFlightRequest request) {
        return !request.getDepartureTime().isAfter(request.getArrivalTime()) && !request.getDepartureTime().equals(request.getArrivalTime());
    }

    public Flight searchFlightById(long id) {

        System.out.println("Saved Flights: " + flightInMemoryRepository.showSavedFlights());

        Flight foundFlight = flightInMemoryRepository.showSavedFlights().stream()
                .filter(flight -> flight.getId() == id)
                .findFirst()
                .orElse(null);
        System.out.println("Found Flight: " + foundFlight);
        return foundFlight;
    }

    public void deleteFlightById(long id) {
        flightInMemoryRepository.deleteFlight(id);
    }

    public List<Airport> searchAirportsByPhrase(String phrase) {
        return flightInMemoryRepository.showSavedFlights().stream()
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

        List<Flight> flights = flightInMemoryRepository.showSavedFlights().stream()
                .filter(flight -> flight.getFrom().getAirport().equals(request.getFrom()))
                .filter(flight -> flight.getTo().getAirport().equals(request.getTo()))
                .filter(flight -> flight.getDepartureTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                        .equals(request.getDepartureDate()))
                .toList();

        return new PageResult<>(0, flights.size(), flights);
    }

}

