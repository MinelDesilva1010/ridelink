package com.ridelink.fare_payment_service.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.ridelink.fare_payment_service.model.Payment;
import com.ridelink.fare_payment_service.model.Receipt;
import com.ridelink.fare_payment_service.repository.PaymentRepository;
import com.ridelink.fare_payment_service.repository.ReceiptRepository;

@Service
public class ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final PaymentRepository paymentRepository;

    public ReceiptService(
            ReceiptRepository receiptRepository,
            PaymentRepository paymentRepository) {

        this.receiptRepository = receiptRepository;
        this.paymentRepository = paymentRepository;
    }

    public Receipt generateReceipt(String paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        Receipt receipt = new Receipt();

        receipt.setPaymentId(payment.getId());
        receipt.setRideId(payment.getRideId());
        receipt.setAmount(payment.getAmount());
        receipt.setPaymentStatus(payment.getPaymentStatus());
        receipt.setTransactionId(payment.getTransactionId());
        receipt.setGeneratedAt(LocalDateTime.now());

        return receiptRepository.save(receipt);
    }

    public Receipt getReceipt(String receiptId) {

        return receiptRepository.findById(receiptId)
                .orElseThrow(() -> new RuntimeException("Receipt not found"));
    }
}