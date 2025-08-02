package com.hadef.hotelbooking.service;

import com.hadef.hotelbooking.domain.entity.Booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface BookingService {
    List<Booking> getAllBookings();
    Booking createBooking(Booking booking);
    Booking findBookingByReferenceNo(String bookingReference);
    Booking updateBooking(Booking booking);

    boolean isRoomAvailable(UUID id, LocalDateTime checkInDate, LocalDateTime checkOutDate);

}
