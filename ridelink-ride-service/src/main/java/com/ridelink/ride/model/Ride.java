package com.ridelink.ride.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long passengerId;
    private Long driverId;
    
    private String pickupLocation;
    private String destination;

    @Enumerated(EnumType.STRING)
    private RideStatus status = RideStatus.REQUESTED;
}
