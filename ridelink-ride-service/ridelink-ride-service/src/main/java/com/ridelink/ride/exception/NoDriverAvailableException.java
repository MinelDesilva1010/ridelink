package com.ridelink.ride.exception;

/**
 * Thrown when Driver Service responded successfully but
 * there are simply no available drivers right now.
 * This is a *business* condition, distinct from the service being down.
 */
public class NoDriverAvailableException extends RuntimeException {
    public NoDriverAvailableException(String message) {
        super(message);
    }
}
