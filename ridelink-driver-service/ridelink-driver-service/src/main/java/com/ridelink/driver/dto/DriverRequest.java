package com.ridelink.driver.dto;

import jakarta.validation.constraints.NotBlank;

public class DriverRequest {

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "vehicleNumber is required")
    private String vehicleNumber;

    @NotBlank(message = "vehicleType is required")
    private String vehicleType;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
}
