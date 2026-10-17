package com.ridelink.ride.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "rides")
public class Ride {

    @Id
    private String id;

    private Long passengerId;

    private Long driverId;

    private String pickupLocation;

    private String destination;

    private RideStatus status;

    private LocalDateTime requestedAt;

    private LocalDateTime updatedAt;

    public Ride() {
    }

    public void onCreate() {
        requestedAt = LocalDateTime.now();
        updatedAt = requestedAt;

        if (status == null) {
            status = RideStatus.REQUESTED;
        }
    }

    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public Long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}