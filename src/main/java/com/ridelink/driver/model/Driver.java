package com.ridelink.driver.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "drivers")
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

    private Vehicle vehicle;
}
