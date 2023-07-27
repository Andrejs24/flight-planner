package io.codelex.flightplanner.flight.api.testing.responses;

import io.codelex.flightplanner.flight.domain.Flight;
import io.codelex.flightplanner.flight.response.FlightService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestingFlightControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FlightService flightService;

    @Test
    void clearTest() {


        ResponseEntity<Void> response = restTemplate.postForEntity("/testing-api/clear", null, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Flight> flights = flightService.listFlights().getFlights();
        assertTrue(flights.isEmpty());
    }
}