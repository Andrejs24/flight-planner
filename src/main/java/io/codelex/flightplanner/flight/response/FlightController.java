package io.codelex.flightplanner.flight.response;

import io.codelex.flightplanner.flight.request.CreateFlightRequest;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flight")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    Logger logger = LoggerFactory.getLogger(FlightController.class);

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveFlight(@RequestBody CreateFlightRequest request) {
        logger.info("Flight was created successfully!" + request.getArrival() + " to " + request.getDestination());
        flightService.saveFlight(request);
    }

    @GetMapping("/list")
    public ListFlightResponse listFlights() {
        return flightService.listFlights();
    }
}


