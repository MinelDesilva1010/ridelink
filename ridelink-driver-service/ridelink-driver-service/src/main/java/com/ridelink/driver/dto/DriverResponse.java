package com.ridelink.driver.dto;

import com.ridelink.driver.model.Driver;

/**
 * This class (its JSON shape) IS the contract that Ride Service depends on.
 * Ride Service must never depend on the Driver entity/table directly.
 */
public class DriverResponse {

    private Long id;
    private String name;
    private String vehicleNumber;
    private String vehicleType;
    private boolean available;

    public DriverResponse() {
    }

    public DriverResponse(Driver driver) {
        this.id = driver.getId();
        this.name = driver.getName();
        this.vehicleNumber = driver.getVehicleNumber();
        this.vehicleType = driver.getVehicleType();
        this.available = driver.isAvailable();
    }

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
