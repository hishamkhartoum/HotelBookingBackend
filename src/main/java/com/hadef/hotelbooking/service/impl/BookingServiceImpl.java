package com.hadef.hotelbooking.service.impl;

import com.hadef.hotelbooking.domain.entity.Booking;
import com.hadef.hotelbooking.domain.entity.Notification;
import com.hadef.hotelbooking.domain.entity.Room;
import com.hadef.hotelbooking.domain.entity.User;
import com.hadef.hotelbooking.domain.value.BookingStatus;
import com.hadef.hotelbooking.domain.value.PaymentStatus;
import com.hadef.hotelbooking.exception.InvalidBookingStateAndDateException;
import com.hadef.hotelbooking.exception.NotFoundException;
import com.hadef.hotelbooking.repository.BookingRepository;
import com.hadef.hotelbooking.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final BookingCodeGenerator bookingCodeGenerator;
    private final NotificationService notificationService;

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public Booking createBooking(Booking booking, Room room) {
        User currentLoggedInUser = userService.getCurrentLoggedInUser();
        if(booking.getCheckInDate().isBefore(LocalDateTime.now())) {
            throw new InvalidBookingStateAndDateException("Check In Date is invalid");
        }
        if(booking.getCheckOutDate().isBefore(booking.getCheckInDate())) {
            throw new InvalidBookingStateAndDateException("Check Out Date is invalid");
        }
        if(booking.getCheckInDate().isEqual(booking.getCheckOutDate())) {
            throw new InvalidBookingStateAndDateException("check in date cannot be equal to check out date");
        }
        boolean roomAvailable = isRoomAvailable(room.getId(), booking.getCheckInDate(), booking.getCheckOutDate());
        if(!roomAvailable) {
            throw new InvalidBookingStateAndDateException("Room is not available with id: "+room.getId());
        }
        BigDecimal totalPrice = calculateTotalPrice(room,booking);
        String bookingReference = bookingCodeGenerator.generateBookingReference();
        booking.setRoom(room);
        booking.setUser(currentLoggedInUser);
        booking.setTotalPrice(totalPrice);
        booking.setBookingReference(bookingReference);
        booking.setBookingStatus(BookingStatus.BOOKED);
        booking.setPaymentStatus(PaymentStatus.PENDING);

        bookingRepository.save(booking);
        //generate the payment url which will be sent via mail
        String paymentUrl = "http://localhost:3000/payment/" + bookingReference + "/" + totalPrice;
        log.info("PAYMENT LINK: {}", paymentUrl);

        Notification notification = Notification.builder()
                .recipient(currentLoggedInUser.getEmail())
                .subject("Booking Confirmation")
                .body(String.format(
                        "Your booking has been created successfully. Please proceed with your payment using the payment link below " +
                                "\n%s", paymentUrl))
                .bookingReference(bookingReference)
                .build();
        notificationService.sendEmail(notification);
        return booking;
    }


    @Override
    public Booking findBookingByReferenceNo(String bookingReference) {
        return bookingRepository.findByBookingReference(bookingReference).orElseThrow(
                ()-> new NotFoundException("Booking not found with reference: "+bookingReference)
        );
    }

    @Override
    public Booking updateBooking(Booking booking,Room room) {
        if(booking.getId() == null){
            throw new NotFoundException("Booking id is null");
        }
        Booking existingBooking = bookingRepository.findById(booking.getId()).orElseThrow(
                () -> new NotFoundException(
                        "Booking not found with id: " + booking.getId()
                ));
        if(booking.getCheckInDate() != null){
            existingBooking.setCheckInDate(booking.getCheckInDate());
        }
        if(booking.getCheckOutDate() != null){
            existingBooking.setCheckOutDate(booking.getCheckOutDate());
        }
        if(booking.getTotalPrice() != null){
            existingBooking.setTotalPrice(booking.getTotalPrice());
        }
        if(booking.getBookingStatus() != null){
            existingBooking.setBookingStatus(booking.getBookingStatus());
        }
        if(booking.getPaymentStatus() != null){
            existingBooking.setPaymentStatus(booking.getPaymentStatus());
        }

        existingBooking.setRoom(room);
        return bookingRepository.save(existingBooking);
    }

    @Override
    public boolean isRoomAvailable(UUID id, LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        return bookingRepository.isRoomAvailable(id,checkInDate,checkOutDate);
    }

    @Override
    public void updateBookingPaymentStatus(Booking booking) {
        bookingRepository.save(booking);
    }

    private BigDecimal calculateTotalPrice(Room room, Booking booking) {
        BigDecimal totalPrice = room.getPricePerNight();
        long days = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
        return totalPrice.multiply(BigDecimal.valueOf(days));
    }
}
