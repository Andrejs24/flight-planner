package io.codelex.flightplanner.flight.response;

import io.codelex.flightplanner.flight.domain.Flight;
import io.codelex.flightplanner.flight.request.CreateFlightRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    Logger logger = LoggerFactory.getLogger(FlightController.class);

    @PutMapping("/admin-api/flights")
    public ResponseEntity<Flight> addFlight(@RequestBody CreateFlightRequest request) {
        logger.info("Flight was created successfully!" + request.getFrom() + " to " + request.getTo());
        Flight addedFlight = flightService.addFlight(request);
     return   ResponseEntity.status(HttpStatus.CREATED).body(addedFlight);

    }

    @GetMapping("/flight/list")
    public ListFlightResponse listFlights() {
        return flightService.listFlights();
    }

    @PostMapping("/testing-api/clear")
    @ResponseStatus(HttpStatus.OK)
    public void clear() {
        flightService.clear();
    }
}

//    @PutMapping("flights")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void addFlight
//}


