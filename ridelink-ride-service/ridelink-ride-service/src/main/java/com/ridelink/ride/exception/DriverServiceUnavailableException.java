package com.ridelink.ride.exception;

/**
 * Thrown when Driver Service cannot be reached at all
 * (connection refused, timeout, or a 5xx from Driver Service).
 * Maps to HTTP 503 - see GlobalExceptionHandler.
 */
public class DriverServiceUnavailableException extends RuntimeException {
    public DriverServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public DriverServiceUnavailableException(String message) {
        super(message);
    }
}
