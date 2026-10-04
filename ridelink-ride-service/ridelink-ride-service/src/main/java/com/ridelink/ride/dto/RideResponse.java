package com.ridelink.ride.dto;

import com.ridelink.ride.model.Ride;
import com.ridelink.ride.model.RideStatus;

public class RideResponse {

    private Long id;
    private String passengerName;
    private String pickup;
    private String destination;
    private Long driverId;
    private RideStatus status;

    public RideResponse() {
    }

    public RideResponse(Ride ride) {
        this.id = ride.getId();
        this.passengerName = ride.getPassengerName();
        this.pickup = ride.getPickup();
        this.destination = ride.getDestination();
        this.driverId = ride.getDriverId();
        this.status = ride.getStatus();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }

    public String getPickup() { return pickup; }
    public void setPickup(String pickup) { this.pickup = pickup; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public Long getDriverId() { return driverId; }
    public void setDriverId(Long driverId) { this.driverId = driverId; }

    public RideStatus getStatus() { return status; }
    public void setStatus(RideStatus status) { this.status = status; }
}
