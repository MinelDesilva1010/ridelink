package com.ridelink.ride.dto;

public class AvailabilityRequest {
    private boolean available;

    public AvailabilityRequest() {
    }

    public AvailabilityRequest(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}
