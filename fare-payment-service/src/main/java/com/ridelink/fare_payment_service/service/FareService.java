package com.ridelink.fare_payment_service.service;

import org.springframework.stereotype.Service;

import com.ridelink.fare_payment_service.model.Fare;
import com.ridelink.fare_payment_service.repository.FareRepository;

@Service
public class FareService {

    private final FareRepository fareRepository;

    public FareService(FareRepository fareRepository) {
        this.fareRepository = fareRepository;
    }

    public Fare calculateFare(Fare fare) {

        double calculatedFare =
                fare.getBaseFare()
                + (fare.getDistanceKm() * fare.getPerKmRate())
                + (fare.getDurationMinutes() * fare.getPerMinuteRate());

        fare.setEstimatedFare(calculatedFare);
        fare.setFinalFare(calculatedFare);

        return fareRepository.save(fare);
    }
}