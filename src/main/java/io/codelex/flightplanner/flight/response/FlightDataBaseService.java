package io.codelex.flightplanner.flight.response;

import io.codelex.flightplanner.flight.domain.Airport;
import io.codelex.flightplanner.flight.domain.Flight;
import io.codelex.flightplanner.flight.domain.PageResult;
import io.codelex.flightplanner.flight.request.CreateFlightRequest;
import io.codelex.flightplanner.flight.request.SearchFlightRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class FlightDataBaseService implements FlightService {


    private final FlightDataBaseRepository flightDataBaseRepository;
    private final AirportDataBaseRepository airportDataBaseRepository;

    public FlightDataBaseService(FlightDataBaseRepository flightDataBaseRepository, AirportDataBaseRepository airportDataBaseRepository) {
        this.flightDataBaseRepository = flightDataBaseRepository;
        this.airportDataBaseRepository = airportDataBaseRepository;
    }

    private static LocalDateTime formatStringToLocalDateTime(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if (dateString.length() == 10) {
            dateString = dateString + " 00:00";
        }

        return LocalDateTime.parse(dateString, formatter);

    }


    @Override
    public ListFlightResponse listFlights() {
        return new ListFlightResponse(flightDataBaseRepository.findAll());
    }

    @Override
    public synchronized boolean isFlightExists(CreateFlightRequest request) {
        String fromCountry = request.getFrom().getCountry();
        String fromCity = request.getFrom().getCity();
        String fromAirport = request.getFrom().getAirport();
        String toCountry = request.getTo().getCountry();
        String toCity = request.getTo().getCity();
        String toAirport = request.getTo().getAirport();
        String carrier = request.getCarrier();
        LocalDateTime arrivalTime = request.getArrivalTime();
        LocalDateTime departureTime = request.getDepartureTime();
        return flightDataBaseRepository.isFlightExist(fromCountry, fromCity, fromAirport, toCountry, toCity, toAirport, carrier, arrivalTime, departureTime);
    }

    @Override
    public synchronized Flight createFlight(@Valid CreateFlightRequest request) {
        if (!tripIsPossible(request)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This is a problem");
        }
        if (!dateIsCorrect(request)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date is not appropriated ");
        }
        if (!isFlightExists(request)) {
            if (!airportDataBaseRepository.isAirportExist(request.getFrom().getAirport())) {
                Airport airport = new Airport(request.getFrom().getCountry(), request.getFrom().getCity(), request.getFrom().getAirport());
                airportDataBaseRepository.save(airport);
            }
            if (!airportDataBaseRepository.isAirportExist(request.getTo().getAirport())) {
                Airport airport = new Airport(request.getTo().getCountry(), request.getTo().getCity(), request.getTo().getAirport());
                airportDataBaseRepository.save(airport);
            }
            Airport airportFrom = airportDataBaseRepository.findByAirport(request.getFrom().getAirport());
            Airport airportTo = airportDataBaseRepository.findByAirport(request.getTo().getAirport());

            Flight flight = new Flight(airportFrom, airportTo, request.getCarrier(), request.getArrivalTime(), request.getDepartureTime());
            flightDataBaseRepository.save(flight);
            return flight;
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Flight already exists from " + request.getFrom() + " to " + request.getTo());
        }
    }

    @Override
    public void clear() {
        flightDataBaseRepository.deleteAll();
    }

    @Override
    public boolean tripIsPossible(CreateFlightRequest request) {
        return !(request.getFrom()).getAirport().toLowerCase().trim().equals((request.getTo()).getAirport().toLowerCase().trim());

    }

    @Override
    public synchronized boolean dateIsCorrect(CreateFlightRequest request) {
        return !request.getDepartureTime().isAfter(request.getArrivalTime()) && !request.getDepartureTime().equals(request.getArrivalTime());
    }

    @Override
    public Flight searchFlightById(long id) {
        return flightDataBaseRepository.findFlightById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Airport with such Id is not found"));
    }

    @Override
    public void deleteFlightById(long id) {
        flightDataBaseRepository.deleteById(id);
    }

    @Override
    public synchronized PageResult<Flight> searchFlights(@Valid SearchFlightRequest request) {
        if (request.getFrom().equals(request.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't fly from same airport");

        } else {
            List<Flight> flightsList = flightDataBaseRepository.searchFlights(request.getFrom(), request.getTo(), formatStringToLocalDateTime(request.getDepartureDate()));

            return new PageResult<>(0, flightsList.size(), flightsList);

        }
    }

    @Override
    public List<Airport> searchAirportsByPhrase(String phrase) {
        return airportDataBaseRepository.searchAirportsByPhrase(phrase.toLowerCase().trim());
    }


}


