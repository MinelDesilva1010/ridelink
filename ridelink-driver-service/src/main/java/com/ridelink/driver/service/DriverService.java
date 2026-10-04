package com.ridelink.driver.service;

import com.ridelink.driver.model.Driver;
import com.ridelink.driver.model.DriverStatus;
import com.ridelink.driver.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;

    public Driver registerDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    public Driver getDriverById(Long id) {
        return driverRepository.findById(id).orElseThrow(() -> new RuntimeException("Driver not found"));
    }

    public Driver updateDriverStatus(Long id, DriverStatus newStatus) {
        Driver driver = getDriverById(id);
        driver.setStatus(newStatus);
        return driverRepository.save(driver);
    }

    public Driver updateLocation(Long id, String newLocation) {
        Driver driver = getDriverById(id);
        driver.setCurrentLocation(newLocation);
        return driverRepository.save(driver);
    }

    public List<Driver> getAvailableDriversInArea(String serviceArea) {
        if (serviceArea == null || serviceArea.isEmpty()) {
            return driverRepository.findByStatus(DriverStatus.AVAILABLE);
        }
        return driverRepository.findByStatusAndServiceArea(DriverStatus.AVAILABLE, serviceArea);
    }
}
