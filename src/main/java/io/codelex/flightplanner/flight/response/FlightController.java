package io.codelex.flightplanner.flight.response;

import io.codelex.flightplanner.flight.domain.Flight;
import io.codelex.flightplanner.flight.exeptions.DuplicateFlightException;
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

        try {
            Flight addedFlight = flightService.createFlight(request);
            logger.info("Flight created successfully: " + addedFlight.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(addedFlight);
        } catch (DuplicateFlightException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }


//        try {
//        logger.info("Flight creation request - " + request.getFrom() + " to " + request.getTo());
//       if(flightService.isFlightExists(request)){
//           return ResponseEntity.status(HttpStatus.CONFLICT).build();
//        }
//       Flight addedFlight = flightService.createFlight(request);
//        logger.info("Flight created successfully: " + addedFlight.getId());
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(addedFlight);
//        } catch (DuplicateFlightException e) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//
//    } catch (Exception e) {
//
//        logger.error("Error occurred while adding the flight", e);
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//    }

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



