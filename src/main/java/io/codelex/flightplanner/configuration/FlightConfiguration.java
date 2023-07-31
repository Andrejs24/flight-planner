package io.codelex.flightplanner.configuration;

import io.codelex.flightplanner.flight.response.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlightConfiguration {


    @Bean
    @ConditionalOnProperty(prefix = "flight", name = "service.version", havingValue = "in-memory")
    public FlightService getInMemoryVersion(FlightInMemoryRepository flightInMemoryRepository) {
        return new FlightInMemoryService(flightInMemoryRepository);
    }


    @Bean
    @ConditionalOnProperty(prefix = "flight", name = "service.version", havingValue = "database")
    public FlightService getDatabaseVersion(FlightDataBaseRepository flightDataBaseRepository) {
        return new FlightDataBaseService(flightDataBaseRepository);
    }
}