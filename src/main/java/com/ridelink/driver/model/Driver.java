package com.ridelink.driver.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Identifier linking back to the Account Service
    private Long accountId;

    private String name;

    @Enumerated(EnumType.STRING)
    private DriverStatus status = DriverStatus.OFFLINE;

    // Simulated location like "Downtown" or coordinates "6.92,79.86"
    private String currentLocation;

    private String serviceArea;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;
}
