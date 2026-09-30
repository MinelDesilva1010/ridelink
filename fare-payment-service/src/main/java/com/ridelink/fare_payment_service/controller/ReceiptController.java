package com.ridelink.fare_payment_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ridelink.fare_payment_service.model.Receipt;
import com.ridelink.fare_payment_service.service.ReceiptService;

@RestController
@RequestMapping("/api/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping("/{paymentId}")
    public ResponseEntity<Receipt> generateReceipt(
            @PathVariable String paymentId) {

        return ResponseEntity.ok(
                receiptService.generateReceipt(paymentId)
        );
    }

    @GetMapping("/{receiptId}")
    public ResponseEntity<Receipt> getReceipt(
            @PathVariable String receiptId) {

        return ResponseEntity.ok(
                receiptService.getReceipt(receiptId)
        );
    }
}