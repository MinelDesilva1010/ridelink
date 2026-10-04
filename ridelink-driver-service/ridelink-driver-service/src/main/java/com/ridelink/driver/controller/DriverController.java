package com.ridelink.driver.controller;

import com.ridelink.driver.dto.AvailabilityRequest;
import com.ridelink.driver.dto.DriverRequest;
import com.ridelink.driver.dto.DriverResponse;
import com.ridelink.driver.exception.DriverNotFoundException;
import com.ridelink.driver.model.Driver;
import com.ridelink.driver.repository.DriverRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Driver Service owns Driver + vehicle data and availability rules.
 * This controller IS the contract - Ride Service talks to these
 * endpoints only, never to the Driver entity or the driverdb tables.
 */
@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private final DriverRepository driverRepository;

    public DriverController(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverResponse createDriver(@Valid @RequestBody DriverRequest request) {
        Driver driver = new Driver(request.getName(), request.getVehicleNumber(), request.getVehicleType());
        Driver saved = driverRepository.save(driver);
        return new DriverResponse(saved);
    }

    @GetMapping("/available")
    public List<DriverResponse> listAvailableDrivers() {
        return driverRepository.findByAvailableTrue()
                .stream()
                .map(DriverResponse::new)
                .toList();
    }

    @PatchMapping("/{id}/availability")
    public DriverResponse setAvailability(@PathVariable Long id,
                                           @Valid @RequestBody AvailabilityRequest request) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new DriverNotFoundException(id));
        driver.setAvailable(request.getAvailable());
        Driver saved = driverRepository.save(driver);
        return new DriverResponse(saved);
    }
}
