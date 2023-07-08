package io.codelex.flightplanner.flight.response;

import io.codelex.flightplanner.flight.domain.Flight;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Repository
public class FlightRepository {

   private List<Flight> savedFlights =new ArrayList<>();

public void saveFlights(Flight flight){
    this.savedFlights.add(flight);
}
public List<Flight> showSavedFlights(){
    return savedFlights;
}

}
