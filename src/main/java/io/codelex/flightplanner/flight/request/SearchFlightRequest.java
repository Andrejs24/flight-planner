package io.codelex.flightplanner.flight.request;

import com.fasterxml.jackson.annotation.JsonFormat;


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

