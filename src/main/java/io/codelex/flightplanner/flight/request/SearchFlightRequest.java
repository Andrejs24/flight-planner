package io.codelex.flightplanner.flight.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public class SearchFlightRequest {

    @NotNull
    @NotBlank
    @NotEmpty
    private String from;
    @NotNull
    @NotBlank
    @NotEmpty
    private String to;
    @NotNull
    @NotBlank
    @NotEmpty
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private String departureDate;


    public SearchFlightRequest() {

    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }
}



