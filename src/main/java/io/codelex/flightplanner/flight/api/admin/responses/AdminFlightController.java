package io.codelex.flightplanner.flight.api.admin.responses;


import io.codelex.flightplanner.flight.domain.Flight;
import io.codelex.flightplanner.flight.request.CreateFlightRequest;
import io.codelex.flightplanner.flight.response.FlightService;
import io.codelex.flightplanner.flight.response.ListFlightResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/admin-api")
public class AdminFlightController {

    private final FlightService flightService;

    public AdminFlightController(FlightService flightService) {
        this.flightService = flightService;
    }


    @PutMapping("/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlight(@Valid @RequestBody CreateFlightRequest request) {
        return flightService.createFlight(request);
    }

    @GetMapping("/flights")
    @ResponseStatus(HttpStatus.OK)
    public ListFlightResponse showAllFlights() {
        return flightService.listFlights();
    }


    @DeleteMapping("/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFlightById(@PathVariable long id) {
        try {
            flightService.deleteFlightById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No such flight registered", e);
        }

    }

    @GetMapping("flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Flight searchFlightById(@Valid @PathVariable long id) {
        Flight foundFlight = flightService.searchFlightById(id);
        if (foundFlight == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such flight registered");
        }
        return foundFlight;
    }

}
