package com.ridelink.ride.dto;

import com.ridelink.ride.model.Ride;
import com.ridelink.ride.model.RideStatus;
import java.time.LocalDateTime;

public record RideResponse(
        String id,
        Long passengerId,
        Long driverId,
        String pickupLocation,
        String destination,
        RideStatus status,
        LocalDateTime requestedAt,
        LocalDateTime updatedAt
) {
    public static RideResponse from(Ride r) {
        return new RideResponse(
                r.getId(),
                r.getPassengerId(),
                r.getDriverId(),
                r.getPickupLocation(),
                r.getDestination(),
                r.getStatus(),
                r.getRequestedAt(),
                r.getUpdatedAt()
        );
    }
}