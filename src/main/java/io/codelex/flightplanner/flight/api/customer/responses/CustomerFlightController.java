package io.codelex.flightplanner.flight.api.customer.responses;

import io.codelex.flightplanner.flight.domain.Airport;
import io.codelex.flightplanner.flight.domain.Flight;
import io.codelex.flightplanner.flight.domain.PageResult;
import io.codelex.flightplanner.flight.request.SearchFlightRequest;
import io.codelex.flightplanner.flight.response.FlightService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerFlightController {

    private final FlightService flightService;

    public CustomerFlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/airports")
    @ResponseStatus(HttpStatus.OK)
    public List<Airport> searchAirports(@RequestParam("search") String search) {
        return flightService.searchAirportsByPhrase(search);
    }

    @PostMapping("/flights/search")
    @ResponseStatus(HttpStatus.OK)
    public PageResult<Flight> searchFlights(@Valid @RequestBody SearchFlightRequest request) {
        try {
            return flightService.searchFlights(request);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request!", e);
        }
    }


    @GetMapping("flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Flight searchFlightById(@PathVariable long id) {
        Flight foundFlight = flightService.searchFlightById(id);
        if (foundFlight == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such flight registered");
        }
        return foundFlight;
    }
}
