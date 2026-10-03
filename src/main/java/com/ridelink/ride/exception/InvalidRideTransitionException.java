package com.ridelink.ride.exception;

import com.ridelink.ride.model.RideStatus;

public class InvalidRideTransitionException extends RuntimeException {
    public InvalidRideTransitionException(RideStatus from, RideStatus to) {
        super("Invalid ride status transition: " + from + " -> " + to);
    }
}
