package io.codelex.flightplanner.flight.api.admin.responses;

import io.codelex.flightplanner.flight.domain.Airport;
import io.codelex.flightplanner.flight.domain.Flight;
import io.codelex.flightplanner.flight.request.CreateFlightRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminFlightControllerTest {


    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;

    @Test
    void addFlightTest() {
//Given
        String url = "http://localhost:8080/admin-api/flights";
        CreateFlightRequest request = new CreateFlightRequest();
        Airport airport1 = new Airport();
        Airport airport2 = new Airport();
        airport1.setCity("Riga");
        airport1.setAirport("RIX");
        airport1.setCountry("Latvia");
        airport2.setCountry("Estonia");
        airport2.setCity("Tallinn");
        airport2.setAirport("TAL");
        request.setFrom(airport1);
        request.setTo(airport2);
        request.setCarrier("Airbaltic");
        request.setDepartureTime(LocalDateTime.now());
        request.setArrivalTime(LocalDateTime.now().plusDays(2));
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {

            return;
        }
//When
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);


        RequestEntity requestEntity = new RequestEntity<>(request, headers, HttpMethod.PUT, uri);
        ResponseEntity<Flight> responseEntity = restTemplate.exchange(requestEntity, Flight.class);
//Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        Flight addedFlight = responseEntity.getBody();
        assertNotNull(addedFlight);
    }

}
