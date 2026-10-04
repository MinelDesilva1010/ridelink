package com.ridelink.ride.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String passengerName;
    private String pickup;
    private String destination;

    // Ride Service stores ONLY this reference id.
    // It never reads or writes Driver Service's own tables.
    private Long driverId;

    @Enumerated(EnumType.STRING)
    private RideStatus status;

    public Ride() {
    }

    public Ride(String passengerName, String pickup, String destination, Long driverId, RideStatus status) {
        this.passengerName = passengerName;
        this.pickup = pickup;
        this.destination = destination;
        this.driverId = driverId;
        this.status = status;
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
