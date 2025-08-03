package com.hadef.hotelbooking.service.impl;

import com.hadef.hotelbooking.domain.entity.Booking;
import com.hadef.hotelbooking.domain.entity.Notification;
import com.hadef.hotelbooking.domain.entity.PaymentEntity;
import com.hadef.hotelbooking.domain.value.NotificationType;
import com.hadef.hotelbooking.domain.value.PaymentGateway;
import com.hadef.hotelbooking.domain.value.PaymentStatus;
import com.hadef.hotelbooking.domain.dto.StripePaymentRequestDto;
import com.hadef.hotelbooking.repository.PaymentRepository;
import com.hadef.hotelbooking.service.BookingService;
import com.hadef.hotelbooking.service.NotificationService;
import com.hadef.hotelbooking.service.StripePaymentService;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class StripePaymentServiceImpl implements StripePaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingService bookingService;
    private final NotificationService notificationService;

    @Value("${stripe.api.secret.key}")
    private String stripeSecretKey;

    @Override
    public String createPaymentIntent(StripePaymentRequestDto paymentRequest) {
        log.info("Creating Stripe Payment Request for booking reference: {}",
                paymentRequest.getBookingReference());
        Stripe.apiKey = stripeSecretKey;
        String bookingReference = paymentRequest.getBookingReference();
        Booking booking = bookingService.findBookingByReferenceNo(bookingReference);
        if(booking.getPaymentStatus() == PaymentStatus.COMPLETED){
            throw new IllegalStateException("Stripe Payment Request has already been completed");
        }
        try{
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(paymentRequest.getAmount().multiply(BigDecimal.valueOf(100)).longValue()) //amount cents
                    .setCurrency("usd")
                    .putMetadata("bookingReference", bookingReference)
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);
            return intent.getClientSecret();
        }catch(Exception e){
            throw new RuntimeException("Error creating payment intent");
        }
    }

    @Override
    public void updatePaymentBooking(StripePaymentRequestDto paymentRequest) {
        String bookingReference = paymentRequest.getBookingReference();
        log.info("Updating stripe payment for booking reference: {}",
                bookingReference);
        Booking existingBooking = bookingService.findBookingByReferenceNo(bookingReference);
        PaymentEntity payment = PaymentEntity.builder()
                .paymentGateway(PaymentGateway.STRIPE)
                .amount(paymentRequest.getAmount())
                .transactionId(paymentRequest.getTransactionId())
                .paymentStatus(paymentRequest.isSuccess()?PaymentStatus.COMPLETED:PaymentStatus.FAILED)
                .paymentDate(LocalDateTime.now())
                .bookingReference(bookingReference)
                .user(existingBooking.getUser())
                .build();
        if(!paymentRequest.isSuccess()){
            payment.setFailureReason(paymentRequest.getFailureReason());
        }
        paymentRepository.save(payment);

        Notification notification = Notification.builder()
                .recipient(existingBooking.getUser().getEmail())
                .type(NotificationType.EMAIL)
                .bookingReference(bookingReference)
                .build();
        log.info("About to send notification inside updatePaymentBooking  by Email");

        if (paymentRequest.isSuccess()){
            existingBooking.setPaymentStatus(PaymentStatus.COMPLETED);
            bookingService.updateBookingPaymentStatus(existingBooking); //Update the booking

            notification.setSubject("Booking Payment Successful");
            notification.setBody("Congratulation!! Your payment for booking with reference: " + bookingReference + "is successful");
            notificationService.sendEmail(notification); //send email

        }else {

            existingBooking.setPaymentStatus(PaymentStatus.FAILED);
            bookingService.updateBookingPaymentStatus(existingBooking); //Update the booking

            notification.setSubject("Booking Payment Failed");
            notification.setBody("Your payment for booking with reference: " + bookingReference + "failed with reason: " + paymentRequest.getFailureReason());
            notificationService.sendEmail(notification); //send email
        }
    }
}
