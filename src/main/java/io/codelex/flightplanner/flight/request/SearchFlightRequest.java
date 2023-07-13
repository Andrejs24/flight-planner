package io.codelex.flightplanner.flight.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.codelex.flightplanner.flight.domain.Airport;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SearchFlightRequest <T>{

    private T from;

    private T to;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-DD")
    private String departureDate;


    public SearchFlightRequest() {
    }

    public T getFrom() {
        return from;
    }

    public void setFrom(T from) {
        this.from = from;
    }

    public T getTo() {
        return to;
    }

    public void setTo(T to) {
        this.to = to;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }
}

