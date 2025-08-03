package com.hadef.hotelbooking.controller;

import com.hadef.hotelbooking.domain.dto.StripePaymentRequestDto;
import com.hadef.hotelbooking.service.StripePaymentService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stripe-payments")
@RequiredArgsConstructor
public class StripePaymentController {
    private final StripePaymentService stripePaymentService;

    @PostMapping("/pay")
    public ResponseEntity<String> createPaymentIntent(
            @RequestBody StripePaymentRequestDto stripePaymentRequestDto) throws StripeException {
        return ResponseEntity.ok(stripePaymentService.createPaymentIntent(stripePaymentRequestDto));
    }

    @PutMapping("/update")
    public void updatePaymentBooking(StripePaymentRequestDto paymentRequest) {
        stripePaymentService.updatePaymentBooking(paymentRequest);
    }
}
