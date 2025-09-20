package com.hadef.hotelbooking.service;

import com.hadef.hotelbooking.domain.entity.Booking;
import com.hadef.hotelbooking.domain.entity.Room;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BookingService {
    List<Booking> getAllBookings();
    Booking createBooking(Booking booking, Room room);
    Booking findBookingByReferenceNo(String bookingReference);
    Booking updateBooking(Booking booking,Room room);

    boolean isRoomAvailable(UUID id, LocalDate checkInDate, LocalDate checkOutDate);
    void updateBookingPaymentStatus(Booking booking);
}
