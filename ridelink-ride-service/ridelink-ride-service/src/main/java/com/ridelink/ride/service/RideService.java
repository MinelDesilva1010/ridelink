package com.ridelink.ride.service;

import com.ridelink.ride.client.DriverClient;
import com.ridelink.ride.dto.DriverResponse;
import com.ridelink.ride.dto.RideRequest;
import com.ridelink.ride.dto.RideResponse;
import com.ridelink.ride.exception.RideNotFoundException;
import com.ridelink.ride.model.Ride;
import com.ridelink.ride.model.RideStatus;
import com.ridelink.ride.repository.RideRepository;
import org.springframework.stereotype.Service;

/**
 * All ride business rules live HERE, not in the controller and not
 * in DriverClient. DriverClient only knows how to fetch/reserve a
 * driver over HTTP; RideService decides what that means for a ride.
 */
@Service
public class RideService {

    private final DriverClient driverClient;
    private final RideRepository rideRepository;

    public RideService(DriverClient driverClient, RideRepository rideRepository) {
        this.driverClient = driverClient;
        this.rideRepository = rideRepository;
    }

    public RideResponse createRide(RideRequest request) {
        // Ask DriverClient for a driver. If Driver Service is down or has
        // no drivers, this throws BEFORE any Ride row is ever saved -
        // satisfying "do not create a confirmed ride" on failure.
        DriverResponse driver = driverClient.reserveAvailableDriver();

        Ride ride = new Ride(
                request.getPassengerName(),
                request.getPickup(),
                request.getDestination(),
                driver.getId(),
                RideStatus.CONFIRMED
        );

        Ride saved = rideRepository.save(ride);
        return new RideResponse(saved);
    }

    public RideResponse getRide(Long id) {
        Ride ride = rideRepository.findById(id)
                .orElseThrow(() -> new RideNotFoundException(id));
        return new RideResponse(ride);
    }
}
