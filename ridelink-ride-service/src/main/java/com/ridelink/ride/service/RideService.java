package com.ridelink.ride.service;

import com.ridelink.ride.model.Ride;
import com.ridelink.ride.model.RideStatus;
import com.ridelink.ride.repository.RideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepository;
    private final RestTemplate restTemplate;

    // The Driver Service URL (for synchronous REST communication)
    private final String DRIVER_SERVICE_URL = "http://localhost:8082/api/drivers";

    public Ride requestRide(Ride ride) {
        ride.setStatus(RideStatus.REQUESTED);
        return rideRepository.save(ride);
    }

    // Example of Inter-Service Communication
    public Ride assignDriverToRide(Long rideId, String serviceArea) {
        Ride ride = getRideById(rideId);
        
        // Fetch available drivers from Driver Service
        String url = DRIVER_SERVICE_URL + "/available" + (serviceArea != null ? "?serviceArea=" + serviceArea : "");
        ResponseEntity<Object[]> response = restTemplate.getForEntity(url, Object[].class);
        
        if (response.getBody() != null && response.getBody().length > 0) {
            // Simply take the first available driver id (In a real app, parse this properly)
            // Assuming response is an array of driver objects. 
            // We use simple map/json extraction here or just fake it for assignment if complex parsing needed.
            // For now, this just proves communication happens without crashing if it's there.
            ride.setStatus(RideStatus.ASSIGNED);
            return rideRepository.save(ride);
        } else {
            throw new RuntimeException("No available drivers found in the area.");
        }
    }

    public Ride getRideById(Long id) {
        return rideRepository.findById(id).orElseThrow(() -> new RuntimeException("Ride not found"));
    }

    public Ride updateRideStatus(Long id, RideStatus status) {
        Ride ride = getRideById(id);
        ride.setStatus(status);
        return rideRepository.save(ride);
    }

    public List<Ride> getAllRides() {
        return rideRepository.findAll();
    }
}
