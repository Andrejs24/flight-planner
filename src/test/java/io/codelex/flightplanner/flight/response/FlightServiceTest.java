package io.codelex.flightplanner.flight.response;

import io.codelex.flightplanner.flight.domain.Airport;
import io.codelex.flightplanner.flight.domain.Flight;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class FlightServiceTest {

    @Mock
    FlightInMemoryRepository flightInMemoryRepository;

    @InjectMocks
    FlightService flightService;

    @Test
    void searchAirportsByPhraseTest() {
        //Given
        Airport airport1 = new Airport();
        Airport airport2 = new Airport();
        airport1.setCity("Riga");
        airport1.setAirport("RIX");
        airport1.setCountry("Latvia");
        airport2.setCountry("Estonia");
        airport2.setCity("Tallinn");
        airport2.setAirport("TAL");
        Flight flight = new Flight(1L, airport1, airport2, "Airbaltic", LocalDateTime.now(), LocalDateTime.of(2023, 12, 15, 15, 22));
        List<Flight> actualList = new ArrayList<>();
        actualList.add(flight);

        List<Airport> expectedList = new ArrayList<>();
        expectedList.add(airport1);
        //When
        Mockito.when(flightInMemoryRepository.showSavedFlights()).thenReturn(actualList);
        flightService.searchAirportsByPhrase("RIX");
        //Then
        Assertions.assertEquals(expectedList, flightService.searchAirportsByPhrase("RIX"));
    }
}