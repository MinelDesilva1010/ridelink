package com.ridelink.fare_payment_service.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ridelink.fare_payment_service.model.Payment;
import com.ridelink.fare_payment_service.repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment processPayment(Payment payment) {

        // Simulate payment success or failure
        if (payment.isSimulateFailure()) {
            payment.setPaymentStatus("FAILED");
        } else {
            payment.setPaymentStatus("SUCCESS");
        }

        // Generate a transaction ID
        payment.setTransactionId("TXN-" + UUID.randomUUID());

        return paymentRepository.save(payment);
    }

    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }
}