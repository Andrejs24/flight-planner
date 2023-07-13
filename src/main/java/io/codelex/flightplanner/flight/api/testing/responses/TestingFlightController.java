package io.codelex.flightplanner.flight.api.testing.responses;


import io.codelex.flightplanner.flight.response.FlightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testing-api")
public class TestingFlightController {

    private final FlightService flightService;

    public TestingFlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    Logger logger = LoggerFactory.getLogger(TestingFlightController.class);


    @PostMapping("/clear")
    @ResponseStatus(HttpStatus.OK)
    public void clear() {
        flightService.clear();
    }

}
