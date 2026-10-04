package com.ridelink.driver.model;

import lombok.Data;

// No need for @Entity or @Id here since it will be embedded inside the Driver document
@Data
public class Vehicle {
    private String make;
    private String model;
    private String licensePlate;
    private Integer capacity;
}
