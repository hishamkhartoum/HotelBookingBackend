package com.hadef.hotelbooking.service;

import com.hadef.hotelbooking.domain.entity.Booking;
import com.hadef.hotelbooking.domain.entity.Room;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface BookingService {
    List<Booking> getAllBookings();
    Booking createBooking(Booking booking, Room room);
    Booking findBookingByReferenceNo(String bookingReference);
    Booking updateBooking(Booking booking,Room room);

    boolean isRoomAvailable(UUID id, LocalDateTime checkInDate, LocalDateTime checkOutDate);
    void updateBookingPaymentStatus(Booking booking);
}
