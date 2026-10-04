package com.ridelink.ride.dto;

/**
 * Ride Service's own copy of the Driver Service JSON contract
 * (see DriverController / DriverResponse on port 8081).
 * Ride Service depends on this shape, never on Driver Service's
 * internal classes or its database.
 */
public class DriverResponse {

    private Long id;
    private String name;
    private String vehicleNumber;
    private String vehicleType;
    private boolean available;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}
