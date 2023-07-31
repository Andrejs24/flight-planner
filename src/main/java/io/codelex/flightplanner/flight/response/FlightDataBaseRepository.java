package io.codelex.flightplanner.flight.response;

import io.codelex.flightplanner.flight.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FlightDataBaseRepository extends JpaRepository<Flight, Integer> {

    Optional<Flight> findFlightById(long id);
//    @Query("SELECT e FROM Flight e WHERE e.name like ('%' || :name || '%')")
//    List<Flight> searchByName(@Param("name") String namePhrase);
    Optional<Flight> isFlightExist()

}
