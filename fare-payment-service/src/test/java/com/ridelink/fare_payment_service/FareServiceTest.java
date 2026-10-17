package com.ridelink.fare_payment_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ridelink.fare_payment_service.model.Fare;
import com.ridelink.fare_payment_service.repository.FareRepository;
import com.ridelink.fare_payment_service.service.FareService;

class FareServiceTest {

    @Test
    void calculateFare_shouldCalculateCorrectFare() {

        FareRepository fareRepository = mock(FareRepository.class);
        FareService fareService = new FareService(fareRepository);

        Fare fare = new Fare();

        fare.setRideId("RIDE001");
        fare.setBaseFare(100);
        fare.setDistanceKm(10);
        fare.setPerKmRate(80);
        fare.setDurationMinutes(20);
        fare.setPerMinuteRate(10);

        when(fareRepository.save(fare)).thenReturn(fare);

        Fare result = fareService.calculateFare(fare);

        assertEquals(1100, result.getEstimatedFare());
        assertEquals(1100, result.getFinalFare());

        verify(fareRepository).save(fare);
    }
}