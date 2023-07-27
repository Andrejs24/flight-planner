package io.codelex.flightplanner.flight.api.customer.responses;

import io.codelex.flightplanner.flight.domain.Airport;
import io.codelex.flightplanner.flight.domain.Flight;
import io.codelex.flightplanner.flight.domain.PageResult;
import io.codelex.flightplanner.flight.request.SearchFlightRequest;
import io.codelex.flightplanner.flight.response.FlightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerFlightController {

    private final FlightService flightService;

    public CustomerFlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    Logger logger = LoggerFactory.getLogger(CustomerFlightController.class);

    @GetMapping("/airports")
    public ResponseEntity<List<Airport>> searchAirports(@RequestParam("search") String search) {
        List<Airport> matchingAirports = flightService.searchAirportsByPhrase(search);
        return ResponseEntity.ok(matchingAirports);
    }

    @PostMapping("/flights/search")
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


    @GetMapping("/flights/{id}")
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
