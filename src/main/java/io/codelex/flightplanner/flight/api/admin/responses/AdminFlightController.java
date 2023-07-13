package io.codelex.flightplanner.flight.api.admin.responses;


import io.codelex.flightplanner.flight.domain.Flight;
import io.codelex.flightplanner.flight.exeptions.DuplicateFlightException;
import io.codelex.flightplanner.flight.request.CreateFlightRequest;
import io.codelex.flightplanner.flight.response.FlightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;


@RestController
@RequestMapping("/admin-api")
public class AdminFlightController {

    private final FlightService flightService;

    public AdminFlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    Logger logger = LoggerFactory.getLogger(AdminFlightController.class);


    @PutMapping("/flights")
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

    @DeleteMapping("flights/{id}")
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

    @GetMapping("flights/{id}")
    public ResponseEntity<Flight> searchFlightById(@PathVariable long id) {
        try {
            Flight flightFounded = flightService.searchFlightById(id);
            return ResponseEntity.status(HttpStatus.OK).body(flightFounded);
        } catch (Exception e) {
            logger.info("No such flight registered");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


}
