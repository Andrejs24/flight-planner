package io.codelex.flightplanner.flight.response;

import io.codelex.flightplanner.flight.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FlightDataBaseRepository extends JpaRepository<Flight, Long> {

    Optional<Flight> findFlightById(long id);

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Flight f " +
            "WHERE f.from.country = :fromCountry " +
            "AND f.from.city = :fromCity " +
            "AND f.from.airport = :fromAirport " +
            "AND f.to.country = :toCountry " +
            "AND f.to.city = :toCity " +
            "AND f.to.airport = :toAirport " +
            "AND f.carrier = :carrier")
    boolean isFlightExist(@Param("fromCountry") String fromCountry,
                          @Param("fromCity") String fromCity,
                          @Param("fromAirport") String fromAirport,
                          @Param("toCountry") String toCountry,
                          @Param("toCity") String toCity,
                          @Param("toAirport") String toAirport,
                          @Param("carrier") String carrier);

//@Query("select ")
}
