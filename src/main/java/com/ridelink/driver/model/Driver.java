package com.ridelink.driver.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Document(collection = "Default")
@Data
public class Driver {
    @Id
    private String id;
    
    // Identifier linking back to the Account Service
    private Long accountId;

    private String name;

    private DriverStatus status = DriverStatus.OFFLINE;

    // Simulated location like "Downtown" or coordinates "6.92,79.86"
    private String currentLocation;

    private String serviceArea;

    // In MongoDB, the Vehicle can be embedded directly inside the Driver document
    private Vehicle vehicle;
}
