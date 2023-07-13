package io.codelex.flightplanner.flight.response;

import io.codelex.flightplanner.flight.domain.Airport;
import io.codelex.flightplanner.flight.domain.Flight;
import io.codelex.flightplanner.flight.domain.PageResult;
import io.codelex.flightplanner.flight.exeptions.DuplicateFlightException;
import io.codelex.flightplanner.flight.request.CreateFlightRequest;
import io.codelex.flightplanner.flight.request.SearchFlightRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        if (!tripIsPossible(request)) {
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

    public boolean tripIsPossible(CreateFlightRequest request) {
        return ((request.getFrom()).getAirport().toLowerCase().trim().equals((request.getTo()).getAirport().toLowerCase().trim())) ? false : true;
    }

    public boolean dateIsCorrect(CreateFlightRequest request) {
        return (request.getDepartureTime().isAfter(request.getArrivalTime()) || request.getDepartureTime().equals(request.getArrivalTime())) ? false : true;
    }

    public Flight searchFlightById(long id) {
        Flight flightFounded = flightRepository.showSavedFlights().stream().filter(flight -> flight.getId() == id).findFirst().orElse(null);
        if (flightFounded.equals(null)) {
            throw new NullPointerException("No such flight founded!");
        } else return flightFounded;
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


    public synchronized PageResult<Flight> searchFlights(SearchFlightRequest request) {
        if (request.getFrom() == null || request.getTo() == null || request.getDepartureDate() == null) {
            throw new IllegalArgumentException("Some of values are null!");
        }
        if (request.getFrom().equals(request.getTo())) {
            throw new IllegalArgumentException("Can`t be same airports!");
        }

        List<Flight> flights = flightRepository.showSavedFlights().stream()
                .filter(flight -> flight.getFrom().getAirport().equals(request.getFrom()))
                .filter(flight -> flight.getTo().getAirport().equals(request.getTo()))
                .filter(flight -> flight.getDepartureTime().format(DateTimeFormatter.ofPattern("YYYY-MM-DD"))
                        .equals(request.getDepartureDate()))
                .toList();

        return new PageResult<>(0, flights.size(), flights);
    }

}

