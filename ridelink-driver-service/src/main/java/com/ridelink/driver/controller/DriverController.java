package com.ridelink.driver.controller;

import com.ridelink.driver.model.Driver;
import com.ridelink.driver.model.DriverStatus;
import com.ridelink.driver.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @PostMapping
    public ResponseEntity<Driver> registerDriver(@RequestBody Driver driver) {
        return ResponseEntity.ok(driverService.registerDriver(driver));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Driver> getDriver(@PathVariable Long id) {
        return ResponseEntity.ok(driverService.getDriverById(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Driver> updateStatus(@PathVariable Long id, @RequestParam DriverStatus status) {
        return ResponseEntity.ok(driverService.updateDriverStatus(id, status));
    }

    @PatchMapping("/{id}/location")
    public ResponseEntity<Driver> updateLocation(@PathVariable Long id, @RequestParam String location) {
        return ResponseEntity.ok(driverService.updateLocation(id, location));
    }

    @GetMapping("/available")
    public ResponseEntity<List<Driver>> getAvailableDrivers(@RequestParam(required = false) String serviceArea) {
        return ResponseEntity.ok(driverService.getAvailableDriversInArea(serviceArea));
    }
}
