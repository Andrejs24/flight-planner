package io.codelex.flightplanner.flight.response;

import io.codelex.flightplanner.flight.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AirportDataBaseRepository extends JpaRepository<Airport, Long> {

    Optional<Airport> findAllById(Long id);

    Airport findByAirport(String airport);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Airport a " +
            "WHERE a.airport = :airport " +
            "AND a.city = :city " +
            "AND a.country = :country ")
    boolean isAirportExist(@Param("country") String country,
                           @Param("city") String city,
                           @Param("airport") String airport);

    @Query("SELECT a FROM Airport a " +
            "WHERE LOWER(a.airport) LIKE %:phrase% " +
            "OR LOWER(a.city) LIKE %:phrase% " +
            "OR LOWER(a.country) LIKE %:phrase%")
    List<Airport> searchAirportsByPhrase(@Param("phrase") String phrase);
}
