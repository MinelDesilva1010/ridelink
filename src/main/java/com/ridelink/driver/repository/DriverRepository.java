package com.ridelink.driver.repository;

import com.ridelink.driver.model.Driver;
import com.ridelink.driver.model.DriverStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// Notice it extends MongoRepository, and the ID type is now String
public interface DriverRepository extends MongoRepository<Driver, String> {
    List<Driver> findByStatusAndServiceArea(DriverStatus status, String serviceArea);
    List<Driver> findByStatus(DriverStatus status);
}
