package io.codelex.flightplanner.flight.response;

import io.codelex.flightplanner.flight.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirportDataBaseRepository extends JpaRepository<Airport, Long> {

    Optional<Airport> findAllById(Long id);

    Airport findByAirport(String airport);

}
