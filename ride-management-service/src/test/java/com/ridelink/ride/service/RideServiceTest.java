package com.ridelink.ride.service;

import com.ridelink.ride.dto.CreateRideRequest;
import com.ridelink.ride.exception.InvalidRideTransitionException;
import com.ridelink.ride.model.Ride;
import com.ridelink.ride.model.RideStatus;
import com.ridelink.ride.repository.RideRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class RideServiceTest {

    @Mock
    RideRepository repository;

    @InjectMocks
    RideService service;

    @Test
    void createRideStartsAsRequested() {
        Ride saved = new Ride();
        saved.setPassengerId(101L);
        saved.setPickupLocation("SLIIT Malabe");
        saved.setDestination("Kaduwela");
        saved.setStatus(RideStatus.REQUESTED);

        when(repository.save(any(Ride.class))).thenReturn(saved);

        var result = service.create(
                new CreateRideRequest(
                        101L,
                        "SLIIT Malabe",
                        "Kaduwela"
                )
        );

        assertEquals(RideStatus.REQUESTED, result.status());
    }

    @Test
    void completedRideCannotTransitionAgain() {
        Ride ride = new Ride();
        ride.setStatus(RideStatus.COMPLETED);

        when(repository.findById("ride-1"))
                .thenReturn(Optional.of(ride));

        assertThrows(
                InvalidRideTransitionException.class,
                () -> service.changeStatus(
                        "ride-1",
                        RideStatus.CANCELLED
                )
        );
    }
}