package io.codelex.flightplanner.flight.exeptions;

public class DuplicateFlightException extends RuntimeException{
    public DuplicateFlightException(String message) {
        super(message);
    }
}
