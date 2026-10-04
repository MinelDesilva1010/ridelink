package com.ridelink.driver.repository;

import com.ridelink.driver.model.Driver;
import com.ridelink.driver.model.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> findByStatusAndServiceArea(DriverStatus status, String serviceArea);
    List<Driver> findByStatus(DriverStatus status);
}
