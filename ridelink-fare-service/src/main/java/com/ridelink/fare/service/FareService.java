package com.ridelink.fare.service;

import com.ridelink.fare.model.Payment;
import com.ridelink.fare.model.PaymentStatus;
import com.ridelink.fare.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FareService {

    private final PaymentRepository paymentRepository;

    public Payment calculateFare(Long rideId, Double distanceKm) {
        Double baseFare = 150.0;
        Double perKmRate = 80.0;
        Double totalAmount = baseFare + (distanceKm * perKmRate);

        Payment payment = new Payment();
        payment.setRideId(rideId);
        payment.setAmount(totalAmount);
        payment.setStatus(PaymentStatus.PENDING);
        
        return paymentRepository.save(payment);
    }

    public Payment processPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        
        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setReceiptUrl("/receipts/" + UUID.randomUUID().toString());
        return paymentRepository.save(payment);
    }

    public Payment getPaymentByRideId(Long rideId) {
        return paymentRepository.findByRideId(rideId)
                .orElseThrow(() -> new RuntimeException("Payment not found for ride"));
    }
}
