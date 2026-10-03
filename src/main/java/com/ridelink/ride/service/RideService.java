package com.ridelink.ride.service;

import com.ridelink.ride.dto.CreateRideRequest;
import com.ridelink.ride.dto.RideResponse;
import com.ridelink.ride.exception.InvalidRideTransitionException;
import com.ridelink.ride.exception.RideNotFoundException;
import com.ridelink.ride.model.Ride;
import com.ridelink.ride.model.RideStatus;
import com.ridelink.ride.repository.RideRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideService {

    private final RideRepository rideRepository;

    public RideService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    public RideResponse create(CreateRideRequest request) {

        Ride ride = new Ride();

        ride.setPassengerId(request.passengerId());
        ride.setPickupLocation(request.pickupLocation());
        ride.setDestination(request.destination());
        ride.setStatus(RideStatus.REQUESTED);

        // Set creation timestamps
        ride.onCreate();

        return RideResponse.from(rideRepository.save(ride));
    }

    public RideResponse get(String id) {
        return RideResponse.from(find(id));
    }

    public List<RideResponse> byPassenger(Long passengerId) {
        return rideRepository
                .findByPassengerIdOrderByRequestedAtDesc(passengerId)
                .stream()
                .map(RideResponse::from)
                .toList();
    }

    public List<RideResponse> byDriver(Long driverId) {
        return rideRepository
                .findByDriverIdOrderByRequestedAtDesc(driverId)
                .stream()
                .map(RideResponse::from)
                .toList();
    }

    public RideResponse assign(String id, Long driverId) {

        Ride ride = find(id);

        transition(ride, RideStatus.ASSIGNED);

        ride.setDriverId(driverId);

        ride.onUpdate();

        return RideResponse.from(rideRepository.save(ride));
    }

    public RideResponse changeStatus(String id, RideStatus target) {

        Ride ride = find(id);

        transition(ride, target);

        ride.onUpdate();

        return RideResponse.from(rideRepository.save(ride));
    }

    private Ride find(String id) {

        return rideRepository.findById(id)
                .orElseThrow(() -> new RideNotFoundException(id));
    }

    private void transition(Ride ride, RideStatus target) {

        RideStatus from = ride.getStatus();

        boolean valid = switch (from) {

            case REQUESTED ->
                    target == RideStatus.ASSIGNED ||
                    target == RideStatus.CANCELLED;

            case ASSIGNED ->
                    target == RideStatus.ACCEPTED ||
                    target == RideStatus.CANCELLED;

            case ACCEPTED ->
                    target == RideStatus.IN_PROGRESS ||
                    target == RideStatus.CANCELLED;

            case IN_PROGRESS ->
                    target == RideStatus.COMPLETED;

            case COMPLETED, CANCELLED ->
                    false;
        };

        if (!valid) {
            throw new InvalidRideTransitionException(from, target);
        }

        ride.setStatus(target);
    }
}