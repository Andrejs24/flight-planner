package io.codelex.flightplanner.flight.response;

import io.codelex.flightplanner.flight.domain.Airport;
import io.codelex.flightplanner.flight.domain.Flight;
import io.codelex.flightplanner.flight.domain.PageResult;
import io.codelex.flightplanner.flight.exeptions.DuplicateFlightException;
import io.codelex.flightplanner.flight.request.CreateFlightRequest;
import io.codelex.flightplanner.flight.request.SearchFlightRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    Logger logger = LoggerFactory.getLogger(FlightController.class);

    @PutMapping("/admin-api/flights")
    public ResponseEntity<Flight> addFlight(@RequestBody CreateFlightRequest request) {

        try {
            Flight addedFlight = flightService.createFlight(request);
            logger.info("Flight created successfully: " + addedFlight.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(addedFlight);
        } catch (DuplicateFlightException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (NullPointerException e) {
            logger.info("This is a problem");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (DateTimeException e) {
            logger.info("Or this is a problem");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @DeleteMapping("admin-api/flights/{id}")
    public ResponseEntity<Flight> deleteFlightById(@PathVariable long id) {
        try {
            flightService.deleteFlightById(id);
            logger.info("Flight is successfully deleted");
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            logger.info("No such flight registered");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @GetMapping("admin-api/flights/{id}")
    public ResponseEntity<Flight> searchFlightById(@PathVariable long id) {
        try {
            Flight flightFounded = flightService.searchFlightById(id);
            return ResponseEntity.status(HttpStatus.OK).body(flightFounded);
        } catch (Exception e) {
            logger.info("No such flight registered");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PostMapping("/testing-api/clear")
    @ResponseStatus(HttpStatus.OK)
    public void clear() {
        flightService.clear();
    }

    @GetMapping("/api/airports")
    public ResponseEntity<List<Airport>> searchAirports(@RequestParam("search") String search) {
        List<Airport> matchingAirports = flightService.searchAirportsByPhrase(search);
        return ResponseEntity.ok(matchingAirports);
    }

    @PostMapping("/api/flights/search")
    public ResponseEntity<PageResult<Flight>> searchFlights(@RequestBody SearchFlightRequest request) {
        try {
            PageResult<Flight> flightsFounded = flightService.searchFlights(request);
            logger.info("Reached this step");
            return ResponseEntity.status(HttpStatus.OK).body(flightsFounded);
        } catch (IllegalArgumentException e) {
            logger.info("Bad request!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @GetMapping("/api/flights/{id}")
    public ResponseEntity<Flight> findFlyById(@PathVariable long id) {
        try {
            Flight flightFounded = flightService.searchFlightById(id);
            return ResponseEntity.status(HttpStatus.OK).body(flightFounded);
        } catch (Exception e) {
            logger.info("No such flight registered");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }
    }
}




