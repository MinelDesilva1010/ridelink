package com.ridelink.driver.model;

import lombok.Data;

@Data
public class Vehicle {
    private String make;
    private String model;
    private String licensePlate;
    private Integer capacity;
}
