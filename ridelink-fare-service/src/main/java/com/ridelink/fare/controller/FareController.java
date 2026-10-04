package com.ridelink.fare.controller;

import com.ridelink.fare.model.Payment;
import com.ridelink.fare.service.FareService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fares")
@RequiredArgsConstructor
public class FareController {

    private final FareService fareService;

    @PostMapping("/estimate")
    public ResponseEntity<Payment> calculateFare(@RequestParam Long rideId, @RequestParam Double distanceKm) {
        return ResponseEntity.ok(fareService.calculateFare(rideId, distanceKm));
    }

    @PostMapping("/{paymentId}/pay")
    public ResponseEntity<Payment> processPayment(@PathVariable Long paymentId) {
        return ResponseEntity.ok(fareService.processPayment(paymentId));
    }

    @GetMapping("/ride/{rideId}")
    public ResponseEntity<Payment> getPaymentForRide(@PathVariable Long rideId) {
        return ResponseEntity.ok(fareService.getPaymentByRideId(rideId));
    }
}
