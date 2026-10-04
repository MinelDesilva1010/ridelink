package com.ridelink.driver.service;

import com.ridelink.driver.model.Driver;
import com.ridelink.driver.model.DriverStatus;
import com.ridelink.driver.repository.DriverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DriverServiceTest {

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DriverService driverService;

    private Driver driver;

    @BeforeEach
    void setUp() {
        driver = new Driver();
        driver.setId(1L);
        driver.setName("John Doe");
        driver.setStatus(DriverStatus.AVAILABLE);
        driver.setServiceArea("Colombo");
    }

    @Test
    void registerDriver_ShouldSaveAndReturnDriver() {
        when(driverRepository.save(any(Driver.class))).thenReturn(driver);

        Driver savedDriver = driverService.registerDriver(driver);

        assertNotNull(savedDriver);
        assertEquals("John Doe", savedDriver.getName());
        verify(driverRepository, times(1)).save(driver);
    }

    @Test
    void getDriverById_ShouldReturnDriver_WhenExists() {
        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));

        Driver foundDriver = driverService.getDriverById(1L);

        assertNotNull(foundDriver);
        assertEquals(1L, foundDriver.getId());
    }

    @Test
    void getAvailableDriversInArea_ShouldReturnDriverList() {
        when(driverRepository.findByStatusAndServiceArea(DriverStatus.AVAILABLE, "Colombo"))
                .thenReturn(Arrays.asList(driver));

        List<Driver> availableDrivers = driverService.getAvailableDriversInArea("Colombo");

        assertFalse(availableDrivers.isEmpty());
        assertEquals(1, availableDrivers.size());
        assertEquals("Colombo", availableDrivers.get(0).getServiceArea());
    }
}
