package io.codelex.flightplanner.flight.api.admin.responses;


import io.codelex.flightplanner.flight.domain.Flight;
import io.codelex.flightplanner.flight.exeptions.DuplicateFlightException;
import io.codelex.flightplanner.flight.request.CreateFlightRequest;
import io.codelex.flightplanner.flight.response.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.DateTimeException;


@RestController
@RequestMapping("/admin-api")
public class AdminFlightController {

    private final FlightService flightService;

    public AdminFlightController(FlightService flightService) {
        this.flightService = flightService;
    }


    @PutMapping("/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlight(@RequestBody CreateFlightRequest request) {

        try {
            return flightService.createFlight(request);
        } catch (DuplicateFlightException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Flight already exists", e);
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This is a problem", e);
        } catch (DateTimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format", e);
        }

    }

    @DeleteMapping("flights/{id}")
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
    public Flight searchFlightById(@PathVariable long id) {
        try {
            return flightService.searchFlightById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such flight registered", e);

        }
    }


}
