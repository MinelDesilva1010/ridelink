package com.ridelink.ride.controller;

import com.ridelink.ride.dto.AssignDriverRequest;
import com.ridelink.ride.dto.CreateRideRequest;
import com.ridelink.ride.dto.RideResponse;
import com.ridelink.ride.model.RideStatus;
import com.ridelink.ride.service.RideService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rides")
@Tag(name = "Ride Management", description = "Ride request and lifecycle operations")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a ride request")
    public RideResponse create(@Valid @RequestBody CreateRideRequest request) {
        return rideService.create(request);
    }

    @GetMapping("/{id}")
    public RideResponse get(@PathVariable String id) {
        return rideService.get(id);
    }

    @GetMapping("/passenger/{passengerId}")
    public List<RideResponse> byPassenger(@PathVariable Long passengerId) {
        return rideService.byPassenger(passengerId);
    }

    @GetMapping("/driver/{driverId}")
    public List<RideResponse> byDriver(@PathVariable Long driverId) {
        return rideService.byDriver(driverId);
    }

    @PutMapping("/{id}/assign")
    public RideResponse assign(
            @PathVariable String id,
            @Valid @RequestBody AssignDriverRequest request) {

        return rideService.assign(id, request.driverId());
    }

    @PutMapping("/{id}/status/{status}")
    public RideResponse changeStatus(
            @PathVariable String id,
            @PathVariable RideStatus status) {

        return rideService.changeStatus(id, status);
    }

    @PutMapping("/{id}/accept")
    public RideResponse accept(@PathVariable String id) {
        return rideService.changeStatus(id, RideStatus.ACCEPTED);
    }

    @PutMapping("/{id}/start")
    public RideResponse start(@PathVariable String id) {
        return rideService.changeStatus(id, RideStatus.IN_PROGRESS);
    }

    @PutMapping("/{id}/complete")
    public RideResponse complete(@PathVariable String id) {
        return rideService.changeStatus(id, RideStatus.COMPLETED);
    }

    @PutMapping("/{id}/cancel")
    public RideResponse cancel(@PathVariable String id) {
        return rideService.changeStatus(id, RideStatus.CANCELLED);
    }
}