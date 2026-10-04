package com.ridelink.ride.controller;

import com.ridelink.ride.model.Ride;
import com.ridelink.ride.model.RideStatus;
import com.ridelink.ride.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @PostMapping("/request")
    public ResponseEntity<Ride> requestRide(@RequestBody Ride ride) {
        return ResponseEntity.ok(rideService.requestRide(ride));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ride> getRide(@PathVariable Long id) {
        return ResponseEntity.ok(rideService.getRideById(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Ride> updateStatus(@PathVariable Long id, @RequestParam RideStatus status) {
        return ResponseEntity.ok(rideService.updateRideStatus(id, status));
    }

    @GetMapping
    public ResponseEntity<List<Ride>> getAllRides() {
        return ResponseEntity.ok(rideService.getAllRides());
    }
}
