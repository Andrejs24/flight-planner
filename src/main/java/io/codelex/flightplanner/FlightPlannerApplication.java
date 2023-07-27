package io.codelex.flightplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.validation.annotation.Validated;

@SpringBootApplication
@Validated
public class FlightPlannerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightPlannerApplication.class, args);
    }

}
