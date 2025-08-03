package com.hadef.hotelbooking.service;

import com.hadef.hotelbooking.domain.dto.StripePaymentRequestDto;
import com.stripe.exception.StripeException;

public interface StripePaymentService {
    String createPaymentIntent(StripePaymentRequestDto paymentRequest) throws StripeException;
    void updatePaymentBooking(StripePaymentRequestDto paymentRequest);
}
